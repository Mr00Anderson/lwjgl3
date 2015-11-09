/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.ovr.templates

import org.lwjgl.generator.*
import org.lwjgl.ovr.*

val OVR_Util = "OVRUtil".nativeClass(packageName = OVR_PACKAGE, prefixMethod = "ovr", prefixConstant = "ovr") {
	includeOVRCAPI()

	documentation = "Native bindings to the libOVR utility functions."

	// ovrProjectionModifier enum
	IntConstant(
		"""
		Use for generating a default projection matrix that is:
		${ul(
			"Left-handed.",
			"Near depth values stored in the depth buffer are smaller than far depth values.",
			"Both near and far are explicitly defined.",
			"With a clipping range that is (0 to w)."
		)}
		""",
		"Projection_None"..0x00
	)
	IntConstant("Enable if using right-handed transformations in your application.", "Projection_RightHanded"..0x01)
	IntConstant(
		"""
		After the projection transform is applied, far values stored in the depth buffer will be less than closer depth values. NOTE: Enable only if the
		application is using a floating-point depth buffer for proper precision.
		""",
		"Projection_FarLessThanNear"..0x02
	)
	IntConstant(
		"""
		When this flag is used, the zfar value pushed into #Matrix4f_Projection() will be ignored NOTE: Enable only if #Projection_FarLessThanNear is also
		enabled where the far clipping plane will be pushed to infinity.
		""",
		"Projection_FarClipAtInfinity"..0x04
	)
	IntConstant(
		"""
		Enable if the application is rendering with OpenGL and expects a projection matrix with a clipping range of (-w to w). Ignore this flag if your
		application already handles the conversion from D3D range (0 to w) to OpenGL.
		""",
		"Projection_ClipRangeOpenGL"..0x08
	)
	val ProjectionModifiers = "#Projection_None #Projection_RightHanded #Projection_FarLessThanNear #Projection_FarClipAtInfinity #Projection_ClipRangeOpenGL"

	ovrDetectResult(
		"_Detect",
		"""
		Detects Oculus Runtime and Device Status.

		Checks for Oculus Runtime and Oculus HMD device status without loading the LibOVRRT shared library. This may be called before OVR#_Initialize() to help
		decide whether or not to initialize LibOVR.
		""",

		int.IN("timeoutMsec", "a timeout to wait for HMD to be attached or 0 to poll")
	)

	ovrMatrix4f(
		"Matrix4f_Projection",
		"Used to generate projection from {@code ovrEyeDesc::Fov}.",

		ovrFovPort.IN("fov", "the ##OVRFovPort to use"),
		float.IN("znear", "distance to near Z limit"),
		float.IN("zfar", "distance to far Z limit"),
		unsigned_int.IN(
			"projectionModFlags",
			"a combination of the {@code ovrProjectionModifier} flags",
			"#Projection_None #Projection_FarLessThanNear #Projection_FarClipAtInfinity #Projection_ClipRangeOpenGL",
			LinkMode.BITFIELD
		),

		returnDoc = "the calculated projection matrix"
	)

	ovrTimewarpProjectionDesc(
		"TimewarpProjectionDesc_FromProjection",
		"Extracts the required data from the result of #Matrix4f_Projection().",

		ovrMatrix4f.IN("projection", "the project matrix from which to extract ##OVRTimewarpProjectionDesc"),
		unsigned_int.IN("projectionModFlags", "a combination of the ovrProjectionModifier flags", ProjectionModifiers, LinkMode.BITFIELD),

		returnDoc = "the extracted ovrTimewarpProjectionDesc"
	)

	ovrMatrix4f(
		"Matrix4f_OrthoSubProjection",
		"""
		Generates an orthographic sub-projection.

		Used for 2D rendering, Y is down.
		""",

		ovrMatrix4f.IN("projection", "the perspective matrix that the orthographic matrix is derived from"),
		ovrVector2f.IN("orthoScale", "equal to {@code 1.0f / pixelsPerTanAngleAtCenter}"),
		float.IN("orthoDistance", "equal to the distance from the camera in meters, such as 0.8m"),
		float.IN("hmdToEyeViewOffsetX", "the offset of the eye from the center"),

		returnDoc = "the calculated projection matrix"
	)

	void(
		"_CalcEyePoses",
		"Computes offset eye poses based on {@code headPose} returned by ##OVRTrackingState.",

		ovrPosef.IN("headPose", "indicates the HMD position and orientation to use for the calculation"),
		Check(2)..StructBuffer..const..ovrVector3f_p.IN(
			"hmdToEyeViewOffset",
			"""
		    can be ##OVREyeRenderDesc{@code .HmdToEyeViewOffset} returned from OVR#_GetRenderDesc(). For monoscopic rendering, use a vector that is the
		    average of the two vectors for both eyes.
		    """
		),
		Check(2)..StructBuffer..ovrPosef_p.OUT(
			"outEyePoses",
			"""
			if {@code outEyePoses} are used for rendering, they should be passed to OVR#_SubmitFrame() in ##OVRLayerEyeFov{@code ::RenderPose} or
			##OVRLayerEyeFovDepth{@code ::RenderPose}
			"""
		)
	)

	void(
		"_GetEyePoses",
		"""
	    Returns the predicted head pose in {@code outHmdTrackingState} and offset eye poses in {@code outEyePoses}.

		This is a thread-safe function where caller should increment {@code frameIndex} with every frame and pass that index where applicable to functions
		called on the rendering thread. Assuming {@code outEyePoses} are used for rendering, it should be passed as a part of ##OVRLayerEyeFov. The caller does
		not need to worry about applying {@code hmdToEyeViewOffset} to the returned {@code outEyePoses} variables.
	    """,

		ovrSession.IN("session", "an {@code ovrSession} previously returned by OVR#_Create()"),
		long_long.IN("frameIndex", "the targeted frame index, or 0 to refer to one frame after the last time OVR#_SubmitFrame() was called"),
		ovrBool.IN(
			"latencyMarker",
			"""
			Specifies that this call is the point in time where the "App-to-Mid-Photon" latency timer starts from. If a given {@code ovrLayer} provides
			"SensorSampleTimestamp", that will override the value stored here.
			"""
		),
		Check(2)..StructBuffer..const..ovrVector3f_p.IN(
			"hmdToEyeViewOffset",
			"""
		    can be ##OVREyeRenderDesc{@code .HmdToEyeViewOffset} returned from OVR#_GetRenderDesc(). For monoscopic rendering, use a vector that is the
		    average of the two vectors for both eyes.
		    """
		),
		Check(2)..StructBuffer..ovrPosef_p.OUT("outEyePoses", "the predicted eye poses"),
		nullable..ovrTrackingState_p.OUT("outHmdTrackingState", "the predicted ##OVRTrackingState. May be $NULL, in which case it is ignored.")
	)
}