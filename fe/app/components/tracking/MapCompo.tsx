import React, { useState, useEffect } from "react";
import MapView from "react-native-maps";
import {
  PROVIDER_GOOGLE,
  Marker,
  Polyline,
  enableLatestRenderer,
} from "react-native-maps";
import { StyleSheet, View } from "react-native";
import * as Location from "expo-location";

import colors from "@/assets/color.js";

interface Region {
  latitude: number;
  longitude: number;
  latitudeDelta?: number;
  longitudeDelta?: number;
}

interface LatLng {
  latitude: number;
  longitude: number;
}

interface mapProps {
  myLocation: Region;
  initialRegion: Region;
  toiletRegion: Array<Region>;
  drinkingRegion: Array<Region>;
  safezoneRegion: Array<Region>;
  myRoute: Array<LatLng>;
  guideRoute: Array<LatLng>;
}

enableLatestRenderer();

const map = (props: mapProps) => {
  // 마커 이미지 및 지도 Ref 선언 ====================================
  const markerImages = {
    start: require("@/assets/icons/LocationStart.png"),
    my: require("@/assets/icons/LocationMy.png"),
    toilet: require("@/assets/icons/LocationToilet.png"),
    drinking: require("@/assets/icons/LocationDrinking.png"),
    safezone: require("@/assets/icons/LocationSafezone.png"),
    convenience: require("@/assets/icons/LocationConvenience.png"),
  };

  const mapViewRef = React.useRef<MapView>(null);
  // ==============================================

  // 내 위치 =======================================
  // const [myPosition, setMyPosition] = useState({
  //   latitude: 37.50126,
  //   longitude: 127.0395667,
  // });

  // useEffect(() => {
  //   let timer = setInterval(watchMyPos, 3000);

  //   return () => {
  //     clearInterval(timer);
  //   };
  // }, []);

  // const watchMyPos = () => {
  //   Location.watchPositionAsync(
  //     {
  //       accuracy: Location.Accuracy.Balanced,
  //       timeInterval: 300,
  //       distanceInterval: 1,
  //     },
  //     (position) => {
  //       const { latitude, longitude } = position.coords;

  //       let random = Math.random() * 0.0001;
  //       setMyPosition({
  //         latitude: latitude + random,
  //         longitude: longitude + random,
  //       });

  //       // console.log("latitude: ", myPosition.latitude);
  //       // console.log("longitude: ", myPosition.longitude);
  //     },
  //   );
  // };
  // ==============================================

  return (
    <View style={styles.container}>
      <MapView
        ref={mapViewRef}
        provider={PROVIDER_GOOGLE}
        style={styles.map}
        initialRegion={{
          latitude: props.myLocation.latitude,
          longitude: props.myLocation.longitude,
          latitudeDelta: 0.001,
          longitudeDelta: 0.001,
        }}
        showsBuildings={false}
        showsPointsOfInterest={false}
        showsCompass={false}
        showsScale={false}
        showsIndoors={false}
      >
        {/* 산책 시작 위치 마커 */}
        {/* <Marker
          coordinate={{
            latitude: myPosition.latitude,
            longitude: myPosition.longitude,
          }}
          icon={markerImages.start}
          tappable={false}
        /> */}

        {/* 화장실 위치 마커 */}
        {props.toiletRegion[0] !== null &&
          props.toiletRegion.map((toilet, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: toilet.latitude,
                longitude: toilet.longitude,
              }}
              icon={markerImages.toilet}
              tappable={false}
            />
          ))}

        {/* 음수대 위치 마커 */}
        {props.drinkingRegion[0] !== null &&
          props.drinkingRegion.map((drinking, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: drinking.latitude,
                longitude: drinking.longitude,
              }}
              icon={markerImages.drinking}
              tappable={false}
            />
          ))}

        {/* 안심존 위치 마커 */}
        {props.safezoneRegion[0] !== null &&
          props.safezoneRegion.map((safezone, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: safezone.latitude,
                longitude: safezone.longitude,
              }}
              icon={markerImages.safezone}
              tappable={false}
            />
          ))}

        {/* 내 위치 마커*/}
        <Marker
          coordinate={{
            latitude: props.myLocation.latitude,
            longitude: props.myLocation.longitude,
          }}
          icon={markerImages.my}
          tappable={false}
          style={styles.my}
        />

        {/* 내 경로 그리기 */}
        {props.myRoute[0] !== null && (
          <Polyline
            coordinates={props.myRoute}
            strokeColor={"rgba(148, 192, 32, 0.8)"}
            strokeWidth={12}
            lineJoin="round"
          />
        )}

        {/* 가이드 루트 그리기 */}
        {props.guideRoute[0] !== null && (
          <Polyline
            coordinates={props.guideRoute}
            strokeColor={"rgba(208, 238, 117, 0.7)"}
            strokeWidth={10}
            lineCap="round"
          />
        )}
      </MapView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    ...StyleSheet.absoluteFillObject,
    height: "100%",
    width: "100%",
    justifyContent: "flex-end",
    alignItems: "center",
  },
  map: {
    width: "100%",
    height: "100%",
    ...StyleSheet.absoluteFillObject,
  },
  my: {
    zIndex: -1, // works on ios
    elevation: -1, // works on android
  },
});

export default map;
