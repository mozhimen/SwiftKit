package com.mozhimen.guidek.compiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.mozhimen.guidek.annor.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.mozhimen.guidek.annor.GuideKDestination"})
public class GuideKProcessor extends AbstractProcessor {

    private static final String PAGE_TYPE_ACTIVITY = "Activity";
    private static final String PAGE_TYPE_FRAGMENT = "Fragment";
    private static final String PAGE_TYPE_DIALOG = "Dialog";
    private static final String FILE_NAME_DESTINATION = "guideKDestination.json";
    private static final String TAG = "GuideK>>>>> ";

    private Messager _messager;
    private Filer _filer;

    /**
     * 初始化
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        _messager = processingEnvironment.getMessager();
        _messager.printMessage(Diagnostic.Kind.NOTE, TAG + "enter init...");

        _filer = processingEnvironment.getFiler();
    }

    /**
     * 处理注解
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(GuideKDestination.class);
        if (!elements.isEmpty()) {
            HashMap destMap = new HashMap();
            handleDestination(elements, GuideKDestination.class, destMap);

            try {
                FileObject resource = _filer.createResource(StandardLocation.CLASS_OUTPUT, "", FILE_NAME_DESTINATION);
                //app/build/intermediates/javac/debug/classes/目录下
                //app/main/assets/
                String resourcePath = resource.toUri().getPath();
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
                String assetsPath = appPath + "src/main/assets";

                File file = new File(assetsPath);
                if (file.exists()) {
                    file.mkdirs();
                }
                String content = JSON.toJSONString(destMap);

                File outputFile = new File(assetsPath, FILE_NAME_DESTINATION);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                outputFile.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                writer.write(content);
                writer.flush();

                outputStream.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 处理页面路由
     *
     * @param elements
     * @param destinationClass
     * @param destMap
     */
    private void handleDestination(Set<? extends Element> elements, Class<GuideKDestination> destinationClass, HashMap destMap) {
        int indexDefault = 0;
        JSONArray pages = new JSONArray();

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String clazzName = typeElement.getQualifiedName().toString();//clazzName 全类名

            GuideKDestination annotation = typeElement.getAnnotation(destinationClass);
            String url = annotation.pageUrl();
            String title = url.substring(url.lastIndexOf("/") + 1);
            int index = annotation.pageIndex();
            boolean isDefault = annotation.isDefault();
            if (isDefault) {
                indexDefault = index;
            }
            boolean isEnable = annotation.isEnable();
            int id = Math.abs(clazzName.hashCode());
            String destType = getDestinationType(typeElement);//activity, dialog, fragment

            if (destMap.containsKey(url)) {
                _messager.printMessage(Diagnostic.Kind.ERROR, TAG + "different pages has the same pageUrl: " + url);
            } else {
                JSONObject objPage = new JSONObject();
                objPage.put("title", title);
                objPage.put("enable", isEnable);

                JSONObject objPageInfo = new JSONObject();
                objPageInfo.put("url", url);
                objPageInfo.put("index", index);
                objPageInfo.put("id", id);
                objPageInfo.put("clazzName", clazzName);
                objPageInfo.put("destType", destType);
                objPage.put("pageInfo", objPageInfo);

                JSONObject objTabConfig = new JSONObject();
                objTabConfig.put("type", "");
                objTabConfig.put("name", "");
                objTabConfig.put("bitmapDefault", "");
                objTabConfig.put("bitmapSelected", "");
                objTabConfig.put("iconFont", "");
                objTabConfig.put("iconNameDefault", "");
                objTabConfig.put("iconNameSelected", "");
                objTabConfig.put("iconColorDefault", "");
                objTabConfig.put("iconColorSelected", "");
                objPage.put("tabConfig", objTabConfig);

                pages.add(objPage);
            }
        }

        destMap.put("versionCode", 0);
        destMap.put("indexDefault", indexDefault);
        destMap.put("pages", pages);
    }

    /**
     * 获取页面Type
     *
     * @param typeElement
     * @return
     */
    private String getDestinationType(TypeElement typeElement) {
        TypeMirror typeMirror = typeElement.getSuperclass();
        //androidx.fragment.app.Fragment
        String superClazzName = typeMirror.toString();
        if (superClazzName.contains(PAGE_TYPE_ACTIVITY.toLowerCase())) {
            return PAGE_TYPE_ACTIVITY.toLowerCase();
        } else if (superClazzName.contains(PAGE_TYPE_FRAGMENT.toLowerCase())) {
            return PAGE_TYPE_FRAGMENT.toLowerCase();
        } else if (superClazzName.contains(PAGE_TYPE_DIALOG.toLowerCase())) {
            return PAGE_TYPE_DIALOG.toLowerCase();
        }

        //父类类型是类类型,或接口类型
        if (typeMirror instanceof DeclaredType) {
            Element element = ((DeclaredType) typeMirror).asElement();
            if (element instanceof TypeElement) {
                return getDestinationType((TypeElement) element);
            }
        }
        return null;
    }
}