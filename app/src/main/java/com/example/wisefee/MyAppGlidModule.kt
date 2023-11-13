//package com.example.wisefee
//
//import android.content.Context
//import android.graphics.drawable.Drawable
//import com.bumptech.glide.Glide
//import com.bumptech.glide.Registry
//import com.bumptech.glide.annotation.GlideModule
//import com.bumptech.glide.module.AppGlideModule
//
//@GlideModule
//class MyAppGlideModule : AppGlideModule() {
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        registry.prepend(
//            ByteArray::class.java,
//            Drawable::class.java,
//            YourCustomModelLoader.Factory()
//        )
//    }
//}
