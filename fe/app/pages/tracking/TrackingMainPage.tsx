import React, { useState, useEffect } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import TrackingMainTpl from "@/templates/tracking/TrackingMainTpl";
import * as Location from "expo-location";
import axios from "axios";
import { get } from "react-native/Libraries/TurboModule/TurboModuleRegistry";

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

interface routeLatLng extends LatLng {
  orderNum: number;
}

interface Pin {
  lat: number;
  lng: number;
  pinId: number;
  photoURL: string;
}

interface WeatherProps {
  weatherIcon: string;
  temp: number;
  description: string;
}

const TrackingMainPage = ({ route }) => {
  useEffect(() => {
    console.log(route.params);
    console.log(route);
    if (route.params === undefined || route.params === null) {
      return;
    }
    // 산책 시작 시 가이드 경로가 있을 경우 가이드 경로를 불러옴

    axios
      .get(
        `http://j10a208a.p.ssafy.io:8082/api/v1/tracking/${route.params.guideRouteTrackingId}`,
      )
      .then((res) => {
        console.log(res.data.data);
        setGuideRoute(res.data.data.route);
      });
    // console.log(guideRoute.data.data);
  }, [route]);

  // 위치 정보 수집 =======================================
  useEffect(() => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== "granted") {
        console.log("Permission to access location was denied");
        return;
      }

      const {
        coords: { latitude, longitude },
      } = await Location.getCurrentPositionAsync({ accuracy: 5 });

      const location = await Location.reverseGeocodeAsync(
        { latitude, longitude },
        { useGoogleMaps: true },
      );
    })();
  }, []);
  // ==============================================

  // 유저 정보 =======================================
  const [userName, setUserName] = useState<string>("");
  useEffect(() => {
    getNickname();
  }, []);

  const getNickname = async () => {
    const nickname = await AsyncStorage.getItem("nickname");
    if (nickname === null) {
      return;
    }
    setUserName(nickname);
  };

  // 모달 상태 =======================================
  const [completeModalVisiable, setCompleteModalVisiable] =
    useState<boolean>(false);
  const [routeFormModalVisiable, setRouteFormModalVisiable] =
    useState<boolean>(false);

  // 산책 상태 =======================================
  // 0: 산책 시작 전
  // 1: 산책 중
  // 2: 산책 중지
  // 3: 산책 종료
  const [trackingStatus, setTrackingStatus] = useState<number>(0);
  const [orderNum, setOrderNum] = useState<number>(1);

  useEffect(() => {
    if (trackingStatus === 0) {
      getPins(myPosition.latitude, myPosition.longitude);
    } else if (trackingStatus === 1) {
      setDistance(0);
      setTime(0);
      setKcal(0);
      setStartPosition({
        latitude: myPosition.latitude,
        longitude: myPosition.longitude,
      });
      setOrderNum(1);
      getAmenities(myPosition.latitude, myPosition.longitude);
      setMyRoute([]);
    } else if (trackingStatus === 3) {
      setCompleteModalVisiable(true);
      setTrackingStatus(0);
      getPins(myPosition.latitude, myPosition.longitude);
    }
  }, [trackingStatus]);

  const clearData = () => {
    setDistance(0);
    setTime(0);
    setKcal(0);
    setOrderNum(1);
    setMyRoute([]);
    setGuideRoute([]);
    adjacentToilet.clear();
    adjacentDrinking.clear();
    adjacentSafezone.clear();
  };

  // Map props =======================================
  const [myPosition, setMyPosition] = useState<LatLng>({
    latitude: 37.50126,
    longitude: 127.0395667,
  });

  useEffect(() => {
    let timer = setInterval(watchMyPos, 5000);

    return () => {
      clearInterval(timer);
    };
  }, [trackingStatus]);

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

        // 이전 위치와 현재 위치를 비교하여 15m 이상 차이가 나면 setProgressProps, setRoute 호출
        if (calcDistance({ latitude, longitude }) > 15) {
          if (trackingStatus === 1) {
            // 산책 중이라면 route 기록
            setProgressProps({ latitude, longitude });
            setMyRoute([...myRoute, { orderNum, latitude, longitude }]);
            getAmenities(latitude, longitude);
            setOrderNum(orderNum + 1);
          } else if (trackingStatus === 0 || trackingStatus === 3) {
            // 산책을 시작하지 않았거나 산책을 종료했다면 pin 받는 axios 호출 활성화
            getPins(latitude, longitude);
          }
        }
      },
    );
  };

  // 산책 시작 위치
  const [startPosition, setStartPosition] = useState<LatLng>({
    latitude: 0,
    longitude: 0,
  });

  // 내 경로
  const [myRoute, setMyRoute] = useState<Array<routeLatLng>>([
    // {
    //   orderNum: 1,
    //   latitude: 37.50109633622652,
    //   longitude: 127.03853019658955,
    // },
    // {
    //   orderNum: 2,
    //   latitude: 37.5001501428935,
    //   longitude: 127.0389481428096,
    // },
    // {
    //   orderNum: 3,
    //   latitude: 37.500595658529335,
    //   longitude: 127.04038462372736,
    // },
    // {
    //   orderNum: 4,
    //   latitude: 37.50151034269312,
    //   longitude: 127.03990447582203,
    // },
    // {
    //   orderNum: 5,
    //   latitude: 37.50109633622652,
    //   longitude: 127.03853019658955,
    // },
  ]);

  // 가이드 경로
  const [guideRoute, setGuideRoute] = useState<Array<LatLng>>([]);

  // 시설물 정보
  const [toiletRegion, setToiletRegion] = useState<Array<LatLng>>([]);
  const [drinkingRegion, setDrinkingRegion] = useState<Array<LatLng>>([]);
  const [safezoneRegion, setSafezoneRegion] = useState<Array<LatLng>>([]);
  const [adjacentToilet, setAdjacentToilet] = useState<Set<number>>(new Set());
  const [adjacentDrinking, setAdjacentDrinking] = useState<Set<number>>(
    new Set(),
  );
  const [adjacentSafezone, setAdjacentSafezone] = useState<Set<number>>(
    new Set(),
  );

  // 주변 편의시설 불러오기 axios 호출 =======================================
  const getAmenities = (lat: number, lng: number) => {
    const params = {
      longitude: lng,
      latitude: lat,
      distance: 300,
    };

    console.log(params);
    // 원래 음수대, 화장실, 안전지대 리스트 비우기
    setToiletRegion([]);
    setDrinkingRegion([]);
    setSafezoneRegion([]);

    axios
      .post(
        `http://j10a208a.p.ssafy.io:8082/api/v1/amenities/by-coordinates`,
        params,
      )
      .then((res) => {
        const amenities = res.data.data;
        const newToiletArray: Array<LatLng> = [];
        const newDrinkingArray: Array<LatLng> = [];
        const newSafezoneArray: Array<LatLng> = [];

        // amenities를 타입에 맞게 변환하여 set
        // amenitiesCodeId가 1이면 음수대(drinking), 2면 화장실(toilet), 3이면 안전지대(safezone)
        amenities.forEach((amenity: any) => {
          if (amenity.amenitiesCodeId === 1) {
            newDrinkingArray.push({
              latitude: amenity.latitude,
              longitude: amenity.longitude,
            });
            adjacentDrinking.add(amenity.amenitiesId);
          } else if (amenity.amenitiesCodeId === 2) {
            newToiletArray.push({
              latitude: amenity.latitude,
              longitude: amenity.longitude,
            });
            adjacentToilet.add(amenity.amenitiesId);
          } else if (amenity.amenitiesCodeId === 3) {
            newSafezoneArray.push({
              latitude: amenity.latitude,
              longitude: amenity.longitude,
            });
            adjacentSafezone.add(amenity.amenitiesId);
          }
        });

        setToiletRegion(newToiletArray);
        setDrinkingRegion(newDrinkingArray);
        setSafezoneRegion(newSafezoneArray);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 핀 정보
  const [pinRegion, setPinRegion] = useState<Array<Pin>>([]);

  // 주변 핀 불러오기 axios 호출 =======================================
  const getPins = (lat: number, lng: number) => {
    axios
      .get(
        `http://j10a208a.p.ssafy.io:8081/api/pins?lat=${lat}&lng=${lng}&radius=${300}`,
      )
      .then((res) => {
        setPinRegion(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // StatusBar props =======================================
  const [gudongLocation, setGudongLocation] = useState("");
  const [weather, setWeather] = useState<WeatherProps>({
    weatherIcon: "",
    temp: 0,
    description: "",
  });
  const [dust, setDust] = useState<number>(0);

  // StatusBar props axios 호출 =======================================
  useEffect(() => {
    // axios 호출
    getGudongLocation(myPosition.latitude, myPosition.longitude);
    getDust(myPosition.latitude, myPosition.longitude);
    getWeather(myPosition.latitude, myPosition.longitude);
  }, []);

  async function getGudongLocation(lat: number, lng: number) {
    // axios 호출
    await axios
      .post(
        `http://j10a208a.p.ssafy.io:8082/api/v1/coordinates/sido/${lat}/${lng}`,
      )
      .then((res) => {
        setGudongLocation(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  async function getWeather(lat: number, lng: number) {
    // axios 호출
    await axios
      .get(
        `http://j10a208a.p.ssafy.io:8084/api/bigdata/weather/condition?lat=${lat}&lon=${lng}`,
      )
      .then((res) => {
        setWeather({
          weatherIcon: res.data.data.icon,
          temp: res.data.data.temp,
          description: res.data.data.description,
        });
      })
      .catch((err) => {
        console.log(err);
      });
  }

  async function getDust(lat: number, lng: number) {
    // axios 호출
    await axios
      .get(
        `http://j10a208a.p.ssafy.io:8084/api/bigdata/weather/dust?lat=${lat}&lon=${lng}`,
      )
      .then((res) => {
        setDust(res.data.data.grade);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  // Progress props =======================================
  const [distance, setDistance] = useState<number>(0);
  const [time, setTime] = useState<number>(0);
  const [kcal, setKcal] = useState<number>(0);

  const haversine = require("haversine");
  const calcDistance = (newLatLng: LatLng): number => {
    // myRoute의 마지막 요소와 newLatLng 사이의 거리를 계산
    if (myRoute.length === 0) return 0;
    const distanceDelta = haversine(myRoute[myRoute.length - 1], newLatLng, {
      unit: "meter",
    });
    // console.log("distanceDelta: ", Math.round(distanceDelta));
    return Math.round(distanceDelta);
  };

  const calcKcal = (distance: number) => {
    return (distance / 0.1) * 7;
  };

  // 이 함수가 위에 있는 watchMyPos 함수에서 호출되어야 함
  const setProgressProps = (newLatLng: LatLng) => {
    setDistance(distance + calcDistance(newLatLng));
    setKcal(calcKcal(distance));
  };

  // TrackingMainTpl props =======================================
  const props = {
    // modal props
    completeModalVisiable: completeModalVisiable,
    setCompleteModalVisiable: setCompleteModalVisiable,
    routeFormModalVisiable: routeFormModalVisiable,
    setRouteFormModalVisiable: setRouteFormModalVisiable,
    clearData: clearData,
    myRoute: myRoute,
    startPosition: startPosition,
    toiletRegion: toiletRegion,
    drinkingRegion: drinkingRegion,
    safezoneRegion: safezoneRegion,
    adjacentDrinking: adjacentDrinking,
    adjacentToilet: adjacentToilet,
    adjacentSafezone: adjacentSafezone,

    // Map props
    region: {
      myLocation: myPosition,
      initialRegion: {
        latitude: 37.50126,
        longitude: 127.0395667,
        latitudeDelta: 0.015,
        longitudeDelta: 0.0121,
      },
      toiletRegion: toiletRegion,
      drinkingRegion: drinkingRegion,
      safezoneRegion: safezoneRegion,
      convenienceRegion: [],
      pinRegion: pinRegion,
      myRoute: myRoute,
      guideRoute: guideRoute,
      setGuideRoute: setGuideRoute,
    },

    // StatusBar props
    userName: userName,
    statusBarProps: {
      trackingStatus: trackingStatus,
      location: gudongLocation,
      weather: weather,
      dust: dust,
    },

    // TrackingStatus props
    trackingStatusProps: {
      trackingStatus: trackingStatus,
      setTrackingStatus: setTrackingStatus,
    },

    // Progress props
    progressProps: {
      distance: distance,
      time: time,
      kcal: kcal,
      trackingStatus: trackingStatus,
      setTime: setTime,
      setTrackingStatus: setTrackingStatus,
    },
  };

  return <TrackingMainTpl props={props} />;
};

export default TrackingMainPage;
