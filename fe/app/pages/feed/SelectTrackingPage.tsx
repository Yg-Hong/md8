import colors from "@/assets/color.js";
import React, { useCallback, useState, useEffect } from "react";
import { StyleSheet, Text, View, Button, SafeAreaView } from "react-native";
import { ScrollView, FlatList } from "react-native-gesture-handler";
import { useNavigation } from "@react-navigation/native";

import MenuBarCompo from "@/components/MenuBarCompo";
import SearchBoxCompo from "@/components/SearchBoxCompo";
import TrackCompo from "@/components/TrackCompo";

import { selectTracking } from "@/api/feed";

export default function SelectTrackingPage() {
  const navigation = useNavigation();
  // 유저별 산책 리스트 가져오기
  const [data, setData] = useState([]);
  useEffect(() => {
    //일단 유저 아이디 임의로 1로 설정해놓음. 추후 recoil에서 가져와야 함
    selectTracking(1, 0, 10).then((response) => {
      setData(response.data.trackingResponses);
      console.log("selectTrackingPage에서 axois 요청");
      console.log("selectTrackingPage에서", response.data.trackingResponses);
    });
  }, []);

  const selectTrack = (item) => {
    // console.log("아이템 뽑을래", item);
    navigation.navigate("FeedCreate", { selectedTrack: item });
  };

  return (
    <View style={styles.container}>
      <MenuBarCompo title="산책로 선택하기" />
      <ScrollView style={styles.scrollContainer}>
        {data.map((item, index) => (
          <View key={index} style={{ marginBottom: 10 }}>
            <TrackCompo result={item} onPress={() => selectTrack(item)} />
          </View>
        ))}
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  scrollContainer: {
    paddingHorizontal: 22,
  },
});
