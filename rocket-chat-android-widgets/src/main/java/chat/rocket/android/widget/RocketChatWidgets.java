package chat.rocket.android.widget;

import android.content.Context;
import chat.rocket.android.widget.fresco.CustomImageFormatConfigurator;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import java.util.HashSet;
import java.util.Set;
import okhttp3.OkHttpClient;

public class RocketChatWidgets {

  public static void initialize(Context context, OkHttpClient okHttpClient) {
    FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    Set<RequestListener> listeners = new HashSet<>();
    listeners.add(new RequestLoggingListener());

    ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory
        .newBuilder(context, okHttpClient)
        .setRequestListeners(listeners)
        .setImageDecoderConfig(CustomImageFormatConfigurator.createImageDecoderConfig())
        .setDownsampleEnabled(true)
        .experiment().setBitmapPrepareToDraw(true)
        .experiment().setPartialImageCachingEnabled(true)
        .build();

    DraweeConfig.Builder draweeConfigBuilder = DraweeConfig.newBuilder();
    CustomImageFormatConfigurator.addCustomDrawableFactories(draweeConfigBuilder);

    Fresco.initialize(context, imagePipelineConfig, draweeConfigBuilder.build());
  }
}