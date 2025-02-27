package org.zipcoder.cmt.client.betterControls;

@SuppressWarnings("CastToIncompatibleInterface")
public final class Mixins {
	private Mixins() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T me(Object object) {
		return (T) object;
	}
	
//	public static AccessCameraFields cameraFields(Camera camera) {
//		return (AccessCameraFields) camera;
//	}
//
//	public static AccessClientPlayerFields clientPlayerFields(LocalPlayer localPlayer) {
//		return (AccessClientPlayerFields) localPlayer;
//	}
//
//	public static AccessKeyMappingFields keyMappingFields(KeyMapping keyMapping) {
//		return (AccessKeyMappingFields) keyMapping;
//	}
//
//	public static AccessPlayerFields playerFields(Player player) {
//		return (AccessPlayerFields) player;
//	}
//
//	public static AccessToggleKeyMappingFields toggleKeyMappingFields(ToggleKeyMapping toggleKeyMapping) {
//		return (AccessToggleKeyMappingFields) toggleKeyMapping;
//	}
}