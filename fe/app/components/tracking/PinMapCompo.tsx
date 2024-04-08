import React, { useState, useEffect } from "react";
// import MapView from "react-native-maps";
import {
  PROVIDER_GOOGLE,
  Marker,
  Polyline,
  enableLatestRenderer,
} from "react-native-maps";
import MapView from "react-native-map-clustering";
import { StyleSheet, View } from "react-native";

import colors from "@/assets/color.js";
import PinCustomMarkerView from "./PinCustomMarkerViewCompo";
import PinModalCompo from "../PinModalCompo";

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

interface Pin {
  lat: number;
  lng: number;
  pinId: number;
  photoURL: string;
}

interface PinMapProps {
  myLocation: Region;
  initialRegion: Region;
  myRoute: Array<LatLng>;
  guideRoute: Array<LatLng>;
  pinRegion: Array<Pin>;
}

enableLatestRenderer();

const pinMap = (props: PinMapProps) => {
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
  const [pinModalVisible, setPinModalVisible] = useState<boolean>(false);
  const [selectedPinUri, setSelectedPinUri] = useState<string>("");

  const openPinModal = (uri: string) => {
    setSelectedPinUri(uri);
    setPinModalVisible(true);
  };

  // const [pinArray, setPinArray] = useState<Array<Pin>>([
  //   {
  //     pinId: 1,
  //     uri: "https://avatars.githubusercontent.com/u/89956603?v=4",
  //     latitude: 37.5010963362265,
  //     longitude: 127.03853019658955,
  //   },
  // ]);

  return (
    <View style={styles.container}>
      {pinModalVisible ? (
        <PinModalCompo
          pinModalVisiable={pinModalVisible}
          setPinModalVisiable={setPinModalVisible}
          images={[{ imageId: 1, authorId: 1, uri: selectedPinUri }]}
        ></PinModalCompo>
      ) : (
        <></>
      )}

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
        showsUserLocation={true}
        showsBuildings={false}
        showsPointsOfInterest={false}
        showsCompass={false}
        showsScale={false}
        showsIndoors={false}
      >
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

        {/* 핀 마커 */}
        {(props.pinRegion[0] !== null || props.pinRegion[0] !== undefined) &&
          props.pinRegion.map((pin, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: pin.lat,
                longitude: pin.lng,
              }}
              style={styles.pin}
              onPress={() => {
                openPinModal(pin.photoURL);
              }}
            >
              <PinCustomMarkerView uri={pin.photoURL} />
            </Marker>
          ))}

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
  pin: {
    width: 50,
    height: 50,
  },
});

export default pinMap;
