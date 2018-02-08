package ldh.felix;

import ldh.felix.util.PathUtil;
import org.apache.felix.framework.util.Util;
import org.apache.felix.main.AutoProcessor;
import org.apache.felix.main.Main;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ldh on 2018/2/8.
 */
public class FelixMain {


    public static final String BUNDLE_DIR_SWITCH = "-b";
    public static final String SHUTDOWN_HOOK_PROP = "felix.shutdown.hook";
    public static final String SYSTEM_PROPERTIES_PROP = "felix.system.properties";
    public static final String SYSTEM_PROPERTIES_FILE_VALUE = "system.properties";
    public static final String CONFIG_PROPERTIES_PROP = "felix.config.properties";
    public static final String CONFIG_PROPERTIES_FILE_VALUE = "config.properties";
    public static final String CONFIG_DIRECTORY = "conf";
    public static Framework m_fwk = null;

    public FelixMain() {
    }

    public static void main(String[] args) throws Exception {
        String bundleDir = null;
        String cacheDir = null;
        boolean expectBundleDir = false;

        for(int i = 0; i < args.length; ++i) {
            if(args[i].equals("-b")) {
                expectBundleDir = true;
            } else if(expectBundleDir) {
                bundleDir = args[i];
                expectBundleDir = false;
            } else {
                cacheDir = args[i];
            }
        }

        if(args.length > 3 || expectBundleDir && bundleDir == null) {
            System.out.println("Usage: [-b <bundle-deploy-dir>] [<bundle-cache-dir>]");
            System.exit(0);
        }

        loadSystemProperties();
        Map<String, String> configProps = loadConfigProperties();
        if(configProps == null) {
            System.err.println("No config.properties found.");
            configProps = new HashMap();
        }

        copySystemProperties((Map)configProps);
        if(bundleDir != null) {
            ((Map)configProps).put("felix.auto.deploy.dir", bundleDir);
        } else {
            ((Map)configProps).put("felix.auto.deploy.dir", PathUtil.getRoot("launch") + "/bundle");
        }

        if(cacheDir != null) {
            ((Map)configProps).put("org.osgi.framework.storage", cacheDir);
        }else {
            ((Map)configProps).put("org.osgi.framework.storage", PathUtil.getRoot("launch") + "/felix-cache");
        }

        String enableHook = (String)((Map)configProps).get("felix.shutdown.hook");
        if(enableHook == null || !enableHook.equalsIgnoreCase("false")) {
            Runtime.getRuntime().addShutdownHook(new Thread("Felix Shutdown Hook") {
                public void run() {
                    try {
                        if(FelixMain.m_fwk != null) {
                            FelixMain.m_fwk.stop();
                            FelixMain.m_fwk.waitForStop(0L);
                        }
                    } catch (Exception var2) {
                        System.err.println("Error stopping framework: " + var2);
                    }

                }
            });
        }

        try {
            FrameworkFactory factory = getFrameworkFactory();
            m_fwk = factory.newFramework((Map)configProps);
            m_fwk.init();
            AutoProcessor.process((Map)configProps, m_fwk.getBundleContext());

            FrameworkEvent event;
            do {
                m_fwk.start();
                event = m_fwk.waitForStop(0L);
            } while(event.getType() == 128);

            System.exit(0);
        } catch (Exception var8) {
            System.err.println("Could not create framework: " + var8);
            var8.printStackTrace();
            System.exit(0);
        }

    }

    private static FrameworkFactory getFrameworkFactory() throws Exception {
        URL url = Main.class.getClassLoader().getResource("META-INF/services/org.osgi.framework.launch.FrameworkFactory");
        if(url != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            try {
                for(String s = br.readLine(); s != null; s = br.readLine()) {
                    s = s.trim();
                    if(s.length() > 0 && s.charAt(0) != 35) {
                        FrameworkFactory var3 = (FrameworkFactory)Class.forName(s).newInstance();
                        return var3;
                    }
                }
            } finally {
                if(br != null) {
                    br.close();
                }

            }
        }

        throw new Exception("Could not find framework factory.");
    }

    public static void loadSystemProperties() {
        URL propURL = null;
        String custom = System.getProperty("felix.system.properties");
        String is;
        if(custom != null) {
            try {
                propURL = new URL(custom);
            } catch (MalformedURLException var11) {
                System.err.print("Main: " + var11);
                return;
            }
        } else {
            File confDir = null;
            is = System.getProperty("java.class.path");
            int index = is.toLowerCase().indexOf("felix.jar");
            int start = is.lastIndexOf(File.pathSeparator, index) + 1;
            if(index >= start) {
                String jarLocation = is.substring(start, index);
                confDir = new File((new File((new File(jarLocation)).getAbsolutePath())).getParent(), "conf");
            } else {
                confDir = new File(System.getProperty("user.dir"), "conf");
            }

            try {
                propURL = (new File(confDir, "system.properties")).toURL();
            } catch (MalformedURLException var10) {
                System.err.print("Main: " + var10);
                return;
            }
        }

        Properties props = new Properties();
        InputStream io = null;

        try {
            io = propURL.openConnection().getInputStream();
            props.load(io);
            io.close();
        } catch (FileNotFoundException var8) {
            ;
        } catch (Exception var9) {
            System.err.println("Main: Error loading system properties from " + propURL);
            System.err.println("Main: " + var9);

            try {
                if(io != null) {
                    io.close();
                }
            } catch (IOException var7) {
                ;
            }

            return;
        }

        Enumeration e = props.propertyNames();

        while(e.hasMoreElements()) {
            String name = (String)e.nextElement();
            System.setProperty(name, Util.substVars(props.getProperty(name), name, (Map)null, (Properties)null));
        }

    }

    public static Map<String, String> loadConfigProperties() {
        URL propURL = null;
        String custom = System.getProperty("felix.config.properties");
        String is;
        String name;
        if(custom != null) {
            try {
                propURL = new URL(custom);
            } catch (MalformedURLException var10) {
                System.err.print("Main: " + var10);
                return null;
            }
        } else {
            File confDir = null;
            is = System.getProperty("java.class.path");
            int index = is.toLowerCase().indexOf("felix.jar");
            int start = is.lastIndexOf(File.pathSeparator, index) + 1;
            if(index >= start) {
                name = is.substring(start, index);
                confDir = new File((new File((new File(name)).getAbsolutePath())).getParent(), "conf");
            } else {
//                confDir = new File(System.getProperty("user.dir"), "conf");
                confDir = new File(PathUtil.getRoot("launch"), "conf");
            }

            try {
                propURL = (new File(confDir, "config.properties")).toURL();
            } catch (MalformedURLException var9) {
                System.err.print("Main: " + var9);
                return null;
            }
        }

        Properties props = new Properties();
        InputStream io = null;

        try {
            io = propURL.openConnection().getInputStream();
            props.load(io);
            io.close();
        } catch (Exception var8) {
            try {
                if(io != null) {
                    io.close();
                }
            } catch (IOException var7) {
                ;
            }

            return null;
        }

        Map<String, String> map = new HashMap();
        Enumeration e = props.propertyNames();

        while(e.hasMoreElements()) {
            name = (String)e.nextElement();
            map.put(name, Util.substVars(props.getProperty(name), name, (Map)null, props));
        }

        return map;
    }

    public static void copySystemProperties(Map configProps) {
        Enumeration e = System.getProperties().propertyNames();

        while(true) {
            String key;
            do {
                if(!e.hasMoreElements()) {
                    return;
                }

                key = (String)e.nextElement();
            } while(!key.startsWith("felix.") && !key.startsWith("org.osgi.framework."));

            configProps.put(key, System.getProperty(key));
        }
    }
}
