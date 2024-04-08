import React, { useState } from "react";
import { StyleSheet, Text, View, TouchableOpacity } from "react-native";
import { Camera, CameraType } from "expo-camera";

const TrackingCameraPage = () => {
  const [type, setType] = useState(CameraType.back);
  const [permission, askPermission] = Camera.useCameraPermissions();

  if (!permission) {
    return;
  }
  if (!permission.granted) {
    return;
  }

  function toggleCameraType() {
    setType(type === CameraType.back ? CameraType.front : CameraType.back);
  }

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={toggleCameraType}>
        <Text>갤러리좀 열어봐</Text>
      </TouchableOpacity>

      <Camera style={styles.camera} type={type}>
        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.button} onPress={toggleCameraType}>
            <Text style={styles.text}>Flip Camera</Text>
          </TouchableOpacity>
        </View>
      </Camera>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
  },
  camera: {
    flex: 1,
  },
  buttonContainer: {
    flex: 1,
    flexDirection: "row",
    backgroundColor: "transparent",
    margin: 64,
  },
  button: {
    flex: 1,
    alignSelf: "flex-end",
    alignItems: "center",
  },
  text: {
    fontSize: 24,
    fontWeight: "bold",
    color: "white",
  },
});

export default TrackingCameraPage;
