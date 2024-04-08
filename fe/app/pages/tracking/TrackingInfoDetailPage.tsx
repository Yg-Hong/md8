import React, { useEffect, useState } from "react";
import { View, Text, Image, StyleSheet } from "react-native";

import colors from "@/assets/color.js";
import SmallButtonCompo from "@/components/SmallButtonCompo";
import TrackingInfoCompo from "@/components/tracking/TrackingInfoCompo";
import FacilityInfoCompo from "@/components/tracking/FacilityInfoCompo";
import ButtonCompo from "@/components/ButtonCompo";
import { ScrollView } from "react-native-gesture-handler";
import RecoCompo from "@/components/tracking/RecoCompo";
import LiteMapCompo from "@/components/tracking/LiteMapCompo";
import TrackingInputPage from "./TrackingInputPage";
import TrackingInfoDetailTpl from "@/templates/tracking/TrackingInfoDetailTpl";
import axios from "axios";
import { useNavigation } from "@react-navigation/native";

interface TrackingInfoDetailProps {
  trackingId: number;
}

interface LatLng {
  latitude: number;
  longitude: number;
}

interface TrackingInfoDetailProps {
  trackingId: number;
  title: string;
  createdAt: string;
  content: string;
  initialRegion: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  };
  startPosition: LatLng;
  myRoute: Array<LatLng>;
  time: number;
  distance: number;
  kcal: number;
  drinking: number;
  safezone: number;
  toilet: number;
  isReco: boolean;

  onPressDelete: () => void;
  onPrsseLetsTracking: () => void;
}

const TrackingInfoDetail = ({ route }) => {
  const [id, setId] = useState<number>(0);
  const { trackingId } = route.params;

  useEffect(() => {
    setId(trackingId);
  }, []);

  useEffect(() => {
    console.log("조회" + id);
    console.log(trackingId);
    getTrackingInfo(id);
  }, [id]);

  const onPressDelete = () => {
    deleteTracking();
  };

  useEffect(() => {
    console.log("조회" + id);
    console.log(trackingId);
    getTrackingInfo(id);
  }, [id]);

  const navigation = useNavigation();

  const onPrsseLetsTracking = () => {
    console.log(props.myRoute);
    console.log(props.trackingId);
    navigation.navigate("TrackingMain", {
      guideRouteTrackingId: props.trackingId,
    });
  };

  const [props, setProps] = useState<TrackingInfoDetailProps>({
    trackingId: trackingId,
    title: "",
    createdAt: "",
    content: "",
    initialRegion: {
      latitude: 0,
      longitude: 0,
      latitudeDelta: 0,
      longitudeDelta: 0,
    },
    startPosition: {
      latitude: 0,
      longitude: 0,
    },
    myRoute: [],
    time: 0,
    distance: 0,
    kcal: 0,
    drinking: 0,
    safezone: 0,
    toilet: 0,
    isReco: false,
    onPressDelete: onPressDelete,
    onPrsseLetsTracking: onPrsseLetsTracking,
  });

  const getTrackingInfo = (trackingId: number) => {
    axios
      .get(`http://j10a208a.p.ssafy.io:8082/api/v1/tracking/${trackingId}`)
      .then((res) => {
        setProps({
          trackingId: res.data.data.trackingId,
          title: res.data.data.title,
          createdAt: res.data.data.createdAt,
          content: res.data.data.content,
          distance: res.data.data.distance,
          kcal: res.data.data.kcal,
          time: res.data.data.time,
          drinking: res.data.data.water,
          safezone: res.data.data.safe,
          toilet: res.data.data.toilet,
          isReco: res.data.data.isRecommend,
          myRoute: res.data.data.route,

          initialRegion: {
            latitude: res.data.data.route[0]
              ? res.data.data.route[0].latitude
              : 127.03853,
            longitude: res.data.data.route[0]
              ? res.data.data.route[0].longitude
              : 37.501,
            latitudeDelta: 0.002,
            longitudeDelta: 0.002,
          },

          startPosition: {
            latitude: res.data.data.route[0]
              ? res.data.data.route[0].latitude
              : 127.03853,
            longitude: res.data.data.route[0]
              ? res.data.data.route[0].longitude
              : 37.501,
          },

          onPressDelete: onPressDelete,
          onPrsseLetsTracking: onPrsseLetsTracking,
        });
        console.log(props);
      })
      .catch((err) => {
        console.log("getTrackingInfo axios 에러", err);
      });
  };

  const deleteTracking = () => {
    console.log("삭제!");
    axios
      .delete(
        `http://j10a208a.p.ssafy.io:8082/api/v1/tracking/${props.trackingId}`,
      )
      .then((res) => {
        console.log(res);
        //Todo: 이전 화면으로 navigate
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return <TrackingInfoDetailTpl {...props} />;
};

export default TrackingInfoDetail;
