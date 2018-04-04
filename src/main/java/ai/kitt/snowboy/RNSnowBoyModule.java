package ai.kitt.snowboy;

import com.facebook.react.common.ReactConstants;
import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import android.os.Handler;
import android.os.Message;

import ai.kitt.snowboy.MsgEnum;
import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.audio.RecordingThread;
import ai.kitt.snowboy.AppResCopy;

public class RNSnowBoyModule extends ReactContextBaseJavaModule {
    private RecordingThread recordingThread;
	private ReactApplicationContext mReactContext;
	private int preVolume = -1;
	
	public RNSnowBoyModule(ReactApplicationContext reactContext) {
        super(reactContext);
		mReactContext = reactContext;
	}
	private static final String TAG = "Snowboy";

    @Override
    public String getName() {
        return "SnowBoy";
    }

    @ReactMethod
    public void initHotword(Promise promise) {
        if (ActivityCompat.checkSelfPermission(mReactContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mReactContext,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            AppResCopy.copyResFromAssetsToSD(mReactContext);
			try {
				recordingThread = new RecordingThread(new Handler() {
					@Override
					public void handleMessage(Message msg) {
						MsgEnum message = MsgEnum.getMsgEnum(msg.what);
						String messageText = (String) msg.obj;

						switch(message) {
							case MSG_ACTIVE:
								//HOTWORD DETECTED. NOW DO WHATEVER YOU WANT TO DO HERE
								sendEvent("msg-active", "MSG_ACTIVE");
								// Log.v(TAG, "MSG_ACTIVE");
								break;
							case MSG_INFO:
								sendEvent("msg-info", "MSG_INFO");
								break;
							case MSG_VAD_SPEECH:
								sendEvent("msg-vad-speech", "MSG_VAD_SPEECH");
								break;
							case MSG_VAD_NOSPEECH:
								sendEvent("msg-vad-nospeech", "NO_SPEECH");
								break;
							case MSG_ERROR:
								sendEvent("msg-error", "MSG_ERROR");
								break;
							default:
								super.handleMessage(msg);
								break;
						}
					}
				}, new AudioDataSaver());
				promise.resolve(true);
			} catch(Exception e) {
				String errorMessage = e.getMessage();
				promise.reject(errorMessage);
			}
        }

    }

    @ReactMethod
    public void start() {
        Log.v(TAG, "Start recording");

        if(recordingThread !=null) {
			recordingThread.startRecording();
        }
    }

    @ReactMethod
    public void stop() {
        Log.v(TAG, "Stop recording");

        if(recordingThread !=null){
            recordingThread.stopRecording();
        }
    }

    @ReactMethod
    public void destroy() {
        recordingThread.stopRecording();
    }
	 
	private void sendEvent(String eventName, String msg) {
        WritableMap params = Arguments.createMap();
        params.putString("value", msg);

        mReactContext
				.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
