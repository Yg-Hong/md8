import React from "react";
import {
  View,
  StyleSheet,
  FlatList,
  Dimensions,
  Image,
  TouchableOpacity,
  Text,
} from "react-native";
import { useNavigation } from "@react-navigation/native";

const { width } = Dimensions.get("window");
const itemWidth = width / 3; // 한 줄에 3개의 게시글

// 예시 데이터 형식
// const data = [{"feedId": 15, "photoURL": "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/6f103ef7-b4bc-4261-8516-9b61778bd2b4_IMG_2275"},
// {"feedId": 14, "photoURL": "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/054d15fc-ddc3-4f5b-8760-552d891cd173_IMG_2275"},
// {"feedId": 13, "photoURL": "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/d4c84f43-53cd-4e16-ad0c-a1ee9195b4f3_IMG_2275"},
// {"feedId": 12, "photoURL": "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/90b7744b-d46d-4ecf-b164-8b2f62bf7bcd_IMG_2275"},
// {"feedId": 11, "photoURL": "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/c3a420e7-1f00-4fd9-b0c8-52cd88f3ff7b_IMG_2275"}]

const FeedGridCompo = ({ data }) => {
  const navigation = useNavigation();

  const goToFeedDetailSearch = (feedId) => {
    navigation.navigate("FeedDetailSearch", { feedId });
  };

  const renderItem = ({ item }) => (
    <View style={styles.item}>
      <TouchableOpacity onPress={() => goToFeedDetailSearch(item.feedId)}>
        <Image source={{ uri: item.photoURL }} style={styles.image} />
      </TouchableOpacity>
    </View>
  );

  return (
    <View style={styles.container}>
      <FlatList
        data={data}
        renderItem={renderItem}
        keyExtractor={(item) => item.feedId.toString()}
        horizontal={false}
        numColumns={3}
        // onEndReached={onEndReached}
        // onEndReachedThreshold={0.8}
        // ListFooterComponent={loading && <ActivityIndicator />}
      />
    </View>
  );
};

export default FeedGridCompo;

const styles = StyleSheet.create({
  //feedgrid 전체 영역
  container: {
    width: width,
    flex: 3,
  },
  //게시물 하나(정사각형)
  item: {
    flex: 1,
    width: itemWidth,
    height: itemWidth,
    maxWidth: itemWidth,
    borderWidth: 2,
    borderColor: "#fff",
  },
  image: {
    width: "100%",
    height: "100%",
    resizeMode: "cover",
  },
});
