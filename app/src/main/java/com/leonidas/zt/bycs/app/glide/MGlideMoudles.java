package com.leonidas.zt.bycs.app.glide;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * Created by mebee on 2018/3/3.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
@GlideModule
public final class MGlideMoudles extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        //设置磁盘缓存及其路径
        int MAX_CACHE_SIZE = 250 * 1024 * 1024;
        String CACHE_FILE_NAME = "imgCache";
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,CACHE_FILE_NAME,MAX_CACHE_SIZE));
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String downloadDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                    CACHE_FILE_NAME;
            //路径---->sdcard/imgCache
            builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, MAX_CACHE_SIZE));
        } else {
            //路径---->/sdcard/Android/data/<application package>/cache/imgCache
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
