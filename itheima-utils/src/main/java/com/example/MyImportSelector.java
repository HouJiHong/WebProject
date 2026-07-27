package com.example;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//是ImportSelector的实现类，告诉spring容器，需要导入哪些类（用于批量导入）
public class MyImportSelector implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.example.HeaderConfig", "com.example.TokenParser"};
    }
}
