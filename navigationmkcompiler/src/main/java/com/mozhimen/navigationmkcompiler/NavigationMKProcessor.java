package com.mozhimen.navigationmkcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.mozhimen.navigationmkannor.NavigationMKDestination;

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
@SupportedAnnotationTypes({"com.mozhimen.navigationmkannor.NavigationMKDestination"})
public class NavigationMKProcessor extends AbstractProcessor {
    private static final String PAGE_TYPE_ACTIVITY = "Activity";
    private static final String PAGE_TYPE_FRAGMENT = "Fragment";
    private static final String PAGE_TYPE_DIALOG = "Dialog";
    private static final String OUTPUT_FILE_NAME = "navigationMKDestination.json";

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "enter init...");

        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(NavigationMKDestination.class);
        //Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(NavigationMKDestinationActivity.class);
        if (!elements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(elements, NavigationMKDestination.class, destMap);
            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME);
                // /app/build/intermediates/javac/debug/classes/目录下
                //app/main/assets
                String resourcePath = resource.toUri().getPath();
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
                String assetsPath = appPath + "src/main/assets";

                File file = new File(assetsPath);
                if (file.exists()) {
                    file.mkdirs();
                }
                String content = JSON.toJSONString(destMap);

                File outputFile = new File(assetsPath, OUTPUT_FILE_NAME);
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

    private void handleDestination(Set<? extends Element> elements, Class<NavigationMKDestination> destinationClass, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            //全类名
            String clazzName = typeElement.getQualifiedName().toString();

            NavigationMKDestination annotation = typeElement.getAnnotation(destinationClass);
            String pageUrl = annotation.pageUrl();
            boolean isStarter = annotation.isStarter();
            int pageId = Math.abs(clazzName.hashCode());

            //Activity,Dialog,Fragment
            String destType = getDestinationType(typeElement);

            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "different pages has the same pageUrl: " + pageUrl);
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("clazzName", clazzName);
                jsonObject.put("pageUrl", pageUrl);
                jsonObject.put("isStarter", isStarter);
                jsonObject.put("pageId", pageId);
                jsonObject.put("destType", destType);

                destMap.put(pageUrl, jsonObject);
            }
        }
    }

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