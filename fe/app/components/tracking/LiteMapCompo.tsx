import React from "react";
import MapView, { Polyline, enableLatestRenderer } from "react-native-maps";

import colors from "@/assets/color.js";

interface Region {
  latitude: number;
  longitude: number;
  latitudeDelta: number;
  longitudeDelta: number;
}

interface LatLng {
  latitude: number;
  longitude: number;
}

interface LiteMapCompoProps {
  initialRegion: Region;
  myRoute: Array<LatLng>;
  startPosition: LatLng;
}

enableLatestRenderer();

const LiteMapCompo = (props: LiteMapCompoProps) => {
  return (
    <MapView
      style={{ width: "100%", height: 200 }}
      region={{
        latitude: props.startPosition.latitude,
        longitude: props.startPosition.longitude,
        latitudeDelta: 0.003,
        longitudeDelta: 0.003,
      }}
      liteMode={true}
      showsBuildings={false}
      showsCompass={false}
      showsScale={false}
      showsTraffic={false}
      showsPointsOfInterest={false}
    >
      <Polyline
        coordinates={props.myRoute}
        strokeColor={colors.green}
        strokeWidth={5}
      />
    </MapView>
  );
};

export default LiteMapCompo;
