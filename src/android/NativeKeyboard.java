package nl.xservices.plugins.nativekeyboard;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nl.xservices.plugins.nativekeyboard.lib.NativeKeyboardImpl;
import nl.xservices.plugins.nativekeyboard.lib.OnNativeKeyboardEventListener;

public class NativeKeyboard extends CordovaPlugin {

  private static final String ACTION_SHOW_MESSENGER = "showMessenger";
  private static final String ACTION_HIDE_MESSENGER = "hideMessenger";

  private CallbackContext _callbackContext;

  private NativeKeyboardImpl impl;

  protected void pluginInitialize() {
    impl = new NativeKeyboardImpl();
    impl.init(cordova.getActivity(), webView.getView());
  }

  @Override
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    this._callbackContext = callbackContext;

    if (ACTION_SHOW_MESSENGER.equals(action)) {
      impl.showMessenger(args.getJSONObject(0), new OnNativeKeyboardEventListener() {
        @Override
        public void onSuccess(JSONObject result) {
          PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, result);
          pluginResult.setKeepCallback(true);
          _callbackContext.sendPluginResult(pluginResult);
        }

        @Override
        public void onError(String errorMessage) {
          _callbackContext.error(errorMessage);
        }
      });
      return true;

    } else if (ACTION_HIDE_MESSENGER.equals(action)) {
      impl.hideMessenger(args.getJSONObject(0), new OnNativeKeyboardEventListener() {
        @Override
        public void onSuccess(JSONObject result) {
          callbackContext.success();
        }

        @Override
        public void onError(String errorMessage) {
          _callbackContext.error(errorMessage);
        }
      });
      return true;
    }

    return false;
  }
}
