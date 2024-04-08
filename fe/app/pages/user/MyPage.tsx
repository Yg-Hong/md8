import React, { useEffect, useState } from "react";
import { View, Text, StyleSheet } from "react-native";
import axios from "axios";
import ProfileCompo from "@/components/ProfileCompo";
import IndexingCompo from "@/components/IndexingCompo";
import TrackCompo from "@/components/TrackCompo";
import { ScrollView } from "react-native-gesture-handler";
import colors from "@/assets/color.js";
import MenuBarCompo from "@/components/MenuBarCompo";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";

interface User {
  id: number;
  email: string;
  ageCode: {
    codeId: number;
    name: string;
    detailId: number;
    detailName: string;
    description: string;
  };
  gender: any; // 아직 데이터가 뭔지 자세히 모름..
  nickName: string;
  level: number;
  totalDistance: number;
  profile: string;
  time: number;
  distance: number;
  trackingCount: number;
  followerCount: number;
  followingCount: number;
}

interface trackingResponse {
  bookmark: number;
  createdAt: string;
  distance: number;
  kcal: number;
  time: number;
  title: string;
  trackingId: number;
}

const MyPage = () => {
  const [user, setUser] = useState({} as User);
  const [trackingResponses, setTrackingResponses] = useState<
    Array<trackingResponse>
  >([]);
  const [savedTrackingResponses, setSavedTrackingResponses] = useState<
    Array<trackingResponse>
  >([]);
  const [myFeedResponses, setMyFeedResponses] = useState([]);
  const [isSelected, setIsSelected] = useState(0);

  const getUser = async () => {
    const userId = await AsyncStorage.getItem("userId");
    await axios
      .get(`http://j10a208a.p.ssafy.io:8080/api/users/${userId}`)
      .then((res) => {
        console.log(res.data.data);
        setUser(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const getUserTotalTracking = async () => {
    const userId = await AsyncStorage.getItem("userId");
    await axios
      .get(
        `http://j10a208a.p.ssafy.io:8082/api/v1/tracking/by-creator?userId=${userId}&page=${1}&size=${20}`,
      )
      .then((res) => {
        console.log(res.data.data);
        const trackingResponses = res.data.data.trackingResponses;
        setTrackingResponses(trackingResponses);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const getUserSavedTracking = async () => {
    const userId = await AsyncStorage.getItem("userId");
    await axios
      .get(
        `http://j10a208a.p.ssafy.io:8082/api/v1/bookmark?userId=${userId}&page=${1}&size=${20}`,
      )
      .then((res) => {
        console.log(res.data.data);
        const savedTrackingResponses = res.data.data.bookmarkResponses;
        setSavedTrackingResponses(savedTrackingResponses);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const getUserFeed = async () => {
    const userId = await AsyncStorage.getItem("userId");
    await axios
      .get(`http://j10a208a.p.ssafy.io:8081/api/feeds/myfeed/${userId}`)
      .then((res) => {
        console.log(res.data.data);
        const myFeedResponses = res.data.data.myFeedThumbnailResponseList;
        setMyFeedResponses(myFeedResponses);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    getUser();
    getUserFeed();
  }, []);

  useEffect(() => {
    if (isSelected === 0) {
      getUserTotalTracking();
    } else if (isSelected === 1) {
      getUserSavedTracking();
    } else if (isSelected === 2) {
      getUserFeed();
    }
  }, [isSelected]);

  const TrackList = () => {
    if (trackingResponses.length === 0) return emptyList("내 산책로");

    const trackingList = trackingResponses.map((trackingResponse) => {
      return (
        <TrackCompo
          result={{
            id: trackingResponse.trackingId,
            title: trackingResponse.title,
            distance: trackingResponse.distance,
            time: trackingResponse.time,
            kcal: trackingResponse.kcal,
            bookmark: trackingResponse.bookmark,
            createdAt: trackingResponse.createdAt,
          }}
          onPress={() => console.log(trackingResponse.trackingId)}
        />
      );
    });

    return trackingList;
  };

  const SavedTrackList = () => {
    if (savedTrackingResponses.length === 0) return emptyList("저장 산책로");

    const savedTrackingList = savedTrackingResponses.map((trackingResponse) => {
      return (
        <TrackCompo
          result={{
            id: trackingResponse.trackingId,
            title: trackingResponse.title,
            distance: trackingResponse.distance,
            time: trackingResponse.time,
            kcal: trackingResponse.kcal,
            bookmark: trackingResponse.bookmark,
            createdAt: trackingResponse.createdAt,
          }}
          onPress={() => console.log(trackingResponse.trackingId)}
        />
      );
    });

    return savedTrackingList;
  };

  const MyFeedList = () => {
    if (myFeedResponses.length === 0) return emptyList("피드");

    // return myFeedList;
  };

  const emptyList = (param: string) => {
    return (
      <View style={styles.emptyContainer}>
        <Text style={styles.emptyText}>{`아직 등록된 ${param}가 없어요`}</Text>
      </View>
    );
  };

  return (
    <ScrollView>
      <MenuBarCompo title="유저페이지" settingButton={true} />
      <View>
        <ProfileCompo
          userId={user.id}
          nickname={user.nickName}
          image={user.profile}
          association="myself"
          rank={user.level}
        />
      </View>
      <View style={styles.userInfoContainer}>
        <View style={styles.infoItemContainer}>
          <View style={styles.infoItemTitleContainer}>
            <Text style={styles.infoItemTitleText}>{`내 산책로`}</Text>
          </View>
          <View style={styles.infoItemContentContainer}>
            <View style={styles.infoItemVariableContainer}>
              <Text style={styles.infoItemVariableText}>
                {user.trackingCount}
              </Text>
            </View>
            <View style={styles.infoItemUnitContainer}>
              <Text style={styles.infoItemUnitText}>{`개`}</Text>
            </View>
          </View>
        </View>

        <View style={styles.infoItemContainer}>
          <View style={styles.infoItemTitleContainer}>
            <Text style={styles.infoItemTitleText}>{`산책거리`}</Text>
          </View>
          <View style={styles.infoItemContentContainer}>
            <View style={styles.infoItemVariableContainer}>
              <Text style={styles.infoItemVariableText}>
                {user.totalDistance}
              </Text>
            </View>
            <View style={styles.infoItemUnitContainer}>
              <Text style={styles.infoItemUnitText}>{`km`}</Text>
            </View>
          </View>
        </View>

        <View style={styles.infoItemContainer}>
          <View style={styles.infoItemTitleContainer}>
            <Text style={styles.infoItemTitleText}>{`팔로워`}</Text>
          </View>
          <View style={styles.infoItemContentContainer}>
            <View style={styles.infoItemVariableContainer}>
              {/*Todo: 1000명이 넘는다면 k로 소수점 표현하기 */}
              <Text style={styles.infoItemVariableText}>
                {user.followerCount}
              </Text>
            </View>
            <View style={styles.infoItemUnitContainer}>
              <Text style={styles.infoItemUnitText}>{`명`}</Text>
            </View>
          </View>
        </View>

        <View style={styles.infoItemContainer}>
          <View style={styles.infoItemTitleContainer}>
            <Text style={styles.infoItemTitleText}>{`팔로잉`}</Text>
          </View>
          <View style={styles.infoItemContentContainer}>
            <View style={styles.infoItemVariableContainer}>
              <Text style={styles.infoItemVariableText}>
                {user.followingCount}
              </Text>
            </View>
            <View style={styles.infoItemUnitContainer}>
              <Text style={styles.infoItemUnitText}>{`명`}</Text>
            </View>
          </View>
        </View>
      </View>
      <View style={styles.indexingContainer}>
        <IndexingCompo
          isSelected={isSelected}
          setIsSelected={setIsSelected}
          indexes={["내 산책", "찜한 산책", "내 피드"]}
          handlePresentModalPress={() => {}}
          filter={false}
        />
      </View>
      <View>
        {isSelected === 0 ? (
          <TrackList />
        ) : isSelected === 1 ? (
          <SavedTrackList />
        ) : (
          <MyFeedList />
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  userInfoContainer: {
    flexDirection: "row",
    height: 160,
    paddingTop: 21,
    paddingLeft: 15,
    paddingRight: 15,
    paddingBottom: 21,
    justifyContent: "space-between",
    alignItems: "center",
    alignSelf: "stretch",
    backgroundColor: colors.lightbeige,
  },
  infoItemContainer: {
    paddingTop: 10,
    paddingBottom: 10,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  infoItemTitleContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  infoItemTitleText: {
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font5",
    fontSize: 16,
  },
  infoItemContentContainer: {
    width: 70,
    height: 70,
    paddingTop: 15,
    paddingLeft: 5,
    paddingRight: 5,
    paddingBottom: 15,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
    borderRadius: 50,
    backgroundColor: "white",
  },
  infoItemVariableContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  infoItemVariableText: {
    width: 60,
    height: 22,
    flexDirection: "column",
    justifyContent: "center",
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 18,
  },
  infoItemUnitContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  infoItemUnitText: {
    width: 60,
    height: 22,
    flexDirection: "column",
    justifyContent: "center",
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font5",
    fontSize: 12,
  },
  indexingContainer: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
  },
  emptyContainer: {
    width: "100%",
    height: 100,
    justifyContent: "center",
    alignItems: "center",
  },
  emptyText: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 16,
  },
});

export default MyPage;
