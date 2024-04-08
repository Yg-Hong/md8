import React, { useState, useEffect } from "react";
import { Button, Image, View, Text, StyleSheet } from "react-native";
import { TouchableOpacity } from "react-native-gesture-handler";
import * as ImagePicker from "expo-image-picker";

import colors from "@/assets/color.js";
import * as Location from "expo-location";

import PinGoBackIcon from "@/assets/icons/PinGoBackIcon";
import PinMakeIcon from "@/assets/icons/PinMakeIcon";
import PinGallery from "@/assets/icons/PinGallery";
import MenuBarCompo from "@/components/MenuBarCompo";

import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useNavigation } from "@react-navigation/native";

interface LatLng {
  latitude: number;
  longitude: number;
}

const TrackingGalleryPage = () => {
  const navigation = useNavigation();

  // 내 위치를 받아오기 위한 변수
  const [myPosition, setMyPosition] = useState<LatLng>({
    latitude: 37.50126,
    longitude: 127.0395667,
  });

  useEffect(() => {
    let timer = setInterval(watchMyPos, 5000);

    return () => {
      clearInterval(timer);
    };
  }, []);

  const watchMyPos = () => {
    Location.watchPositionAsync(
      {
        accuracy: Location.Accuracy.Balanced,
        timeInterval: 300,
        distanceInterval: 1,
      },
      (position) => {
        // console.log("position: ", position.coords);
        const { latitude, longitude } = position.coords;
        setMyPosition({
          latitude: latitude,
          longitude: longitude,
        });
      },
    );
  };

  const [image, setImage] = useState<string>("");

  const pickImage = async () => {
    // No permissions request is necessary for launching the image library
    let result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.All,
      allowsEditing: true,
      aspect: [4, 3],
      quality: 1,
    });

    if (!result.canceled) {
      setImage(result.assets[0].uri);
    }
  };

  //  핀 저장 axios 호출
  const savePin = async () => {
    const userId = await AsyncStorage.getItem("userId");

    if (!image) {
      return;
    }
    console.log("image : " + image);
    const formData = new FormData();
    const blob = new Blob(
      [
        JSON.stringify({
          lat: myPosition.latitude,
          lng: myPosition.longitude,
        }),
      ],
      {
        type: "application/json",
      },
    );

    // formData.append("pinData", blob);
    formData.append("file", {
      uri: image,
      name: "pin.jpg",
      type: "image/jpg",
    });
    console.log(formData);

    axios
      .post(
        `http://j10a208a.p.ssafy.io:8081/api/pins/${userId}?lat=${myPosition.latitude}&lng=${myPosition.longitude}`,
        formData,
      )
      .then((res) => {
        console.log(res);
        navigation.goBack();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <View style={styles.container}>
      <MenuBarCompo title="핀 만들기" />
      {image ? (
        <Image source={{ uri: image }} style={styles.image} />
      ) : (
        <View style={styles.emptyImage}></View>
      )}

      <View style={styles.buttonContaienr}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => {
            navigation.goBack();
          }}
        >
          <PinGoBackIcon />
        </TouchableOpacity>
        <TouchableOpacity style={styles.bigButton} onPress={savePin}>
          <PinMakeIcon />
          <View>
            <Text style={styles.buttonText}>핀 만들기</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={pickImage}>
          <PinGallery />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    gap: 20,
  },
  image: {
    width: "70%",
    height: "60%",
  },
  emptyImage: {
    width: "70%",
    height: "60%",
    backgroundColor: "lightgray",
  },
  buttonContaienr: {
    flexDirection: "row",
    gap: 40,
  },
  button: {
    width: 50,
    height: 50,
    borderRadius: 50,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
  },
  bigButton: {
    borderRadius: 50,
    justifyContent: "center",
    alignItems: "center",
  },
  buttonText: {
    paddingTop: 5,
    color: colors.green,
    fontFamily: "font3",
  },
});

export default TrackingGalleryPage;
