package com.wedy.servicemenu.translate;

import java.lang.reflect.Field;

import android.content.res.XModuleResources;

import com.wedy.servicemenu.R;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;


public class Translate_MAIN implements IXposedHookZygoteInit, IXposedHookInitPackageResources{
    private static String MODULE_PATH = null;

    
    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable    {
        // XResources.setSystemWideReplacement("android", "bool", "config_unplugTurnsOnScreen", false);
        MODULE_PATH = startupParam.modulePath;
        //      XposedBridge.log("XDA_DRM: Zygote Init ...");
    }
    
    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable    {
//      XposedBridge.log("XDA_DRM: Testing to Replacing Nova Strings ...");
        if (!resparam.packageName.equals("com.sonyericsson.android.servicemenu"))
            return;

//      XposedBridge.log("XDA_DRM: Replacing Nova Strings ...");
        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);

      Field[] fields = null;
      try{
      fields = R.string.class.getFields();
      for(final Field field : fields) {
         String name = field.getName(); //name of string
         try{
             int id = field.getInt(R.string.class); //id of string
             try { resparam.res.setReplacement(resparam.packageName, "string", name,modRes.getString(id)); } catch(RuntimeException e) { };
         }catch (Exception ex) {
             //Simply ignore ...
         }
      }
    } catch(RuntimeException e) { };
    
    
     /* try{
      fields = R.array.class.getFields();
      for(final Field field : fields) {
         String name = field.getName(); //name of string
         try{
             int id = field.getInt(R.array.class); //id of string
             try { resparam.res.setReplacement(resparam.packageName, "array", name,modRes.getStringArray(id)); } catch(RuntimeException e) { };
         }catch (Exception ex) {
             //Simply ignore ...
         }
      }
    } catch(RuntimeException e) { };
*/
      
       /* Not needed, but can be used to process drawables
      fields = R.drawable.class.getFields();
      for(final Field field : fields) {
         String name = field.getName(); //name of string
         try{
             int id = field.getInt(R.drawable.class); //id of string
             try { resparam.res.setReplacement(resparam.packageName, "drawable", name,modRes.getDrawable(id)); } catch(RuntimeException e) { };
         }catch (Exception ex) {
             //Simply ignore ...
         }
      }
      */
     
      /*
 // Version Dependent Strings
       try { resparam.res.setReplacement(resparam.packageName, "string", "Battery_NotifMode","Battery logging"); } catch(RuntimeException e) { };
       try { resparam.res.setReplacement(resparam.packageName, "string", "Battery_NotifMode_Des","Battery always under control"); } catch(RuntimeException e) { };
      */
    }
}