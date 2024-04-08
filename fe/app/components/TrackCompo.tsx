import colors from "@/assets/color.js";
import { StyleSheet, Text, View } from "react-native";
import DateTimeCompo from "./DateTimeCompo";
import Title from "@/atoms/Title";
import Distance from "@icons/Distance.svg";
import Time from "@icons/Time.svg";
import Calorie from "@icons/Calorie.svg";
import LikeButton from "@/atoms/LikeButton";
import { TouchableOpacity } from "react-native-gesture-handler";
import { addBookMark } from "@/api/recommend";

// 하나의 트랙 오브젝트(result)를 인자로 받음
/* {
  'id': number,
  'title' :  string,
  'distance' : number (meter),
  'time' : number (sec),
  'kcal' : number (kcal)
  'bookmark': number,
  'createdAt' : string 
} */
export default function TrackCompo({ result, onPress }) {
  const userId = 1;

  // 찜하기 요청 API
  const addToBookMark = () => {
    addBookMark(userId, trackingId).then((response) => {
      // console.log("북마크에 추가됨 ");
    });
  };

  // 받은 경로 정보
  const trackingId = result.id;
  const distance = result.distance;
  const time = result.time;
  const date = result.createdAt;

  // 경로 시간 정보
  const sec = Math.floor(time % 60);
  const min = Math.floor((time / 60) % 60);
  const hour = Math.floor((time / (60 * 60)) % 24);

  // 경로 칼로리 정보 (한시간 250kcal 소모 상정)
  const calorie = Math.floor((250 / 60) * min + (250 / 60) * hour * 60);

  return (
    <TouchableOpacity onPress={onPress}>
      <View style={styles.container}>
        <View style={styles.titleContainer}>
          <View style={styles.titleText}>
            <Title content={result.title}></Title>
          </View>
          <TouchableOpacity style={styles.titleButton} onPress={addToBookMark}>
            <LikeButton></LikeButton>
          </TouchableOpacity>
        </View>

        <View style={styles.middleContainer}>
          <View style={styles.middleText}>
            <Distance width={27.816} height={27.816}></Distance>
            <Text>{distance.toFixed(2)}km</Text>
          </View>

          <View style={styles.middleText}>
            <Time width={27.816} height={27.816}></Time>
            {hour === 0 ? (
              <Text>{min}분</Text>
            ) : (
              <Text>
                {hour}시간 {min}분
              </Text>
            )}
          </View>

          <View style={styles.middleText}>
            <Calorie width={27.816} height={27.816}></Calorie>
            <Text>{calorie.toFixed(0)}kcal</Text>
          </View>
        </View>

        <View style={styles.tailContainer}>
          <DateTimeCompo createdAt={date}></DateTimeCompo>
          <Text style={{ fontSize: 16 }}>|</Text>
          <Text style={styles.tailText}>찜 {result.bookMark}개</Text>
        </View>
      </View>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  container: {
    width: "100%",
    paddingVertical: 20,
    paddingHorizontal: 10,
    gap: 5,
    alignItems: "center",
    justifyContent: "center",
    borderWidth: 1,
    borderColor: colors.beige,
    borderRadius: 8,
  },
  titleContainer: {
    width: "100%",
    flex: 1,
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 5,
    marginBottom: 3,
  },
  titleText: {
    flex: 7,
    flexDirection: "row",
    justifyContent: "flex-start",
    // backgroundColor: "yellow",
  },
  titleButton: {
    flex: 2,
    // position: "absolute",
    // left: -30,
  },
  middleContainer: {
    width: "100%",
    paddingRight: 5,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  middleText: {
    flexDirection: "row",
    alignItems: "center",
    gap: 4,
    color: colors.darkbrown,
    fontFamily: "font4",
  },
  tailContainer: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "flex-end",
    gap: 10,
  },
  tailText: {
    textAlign: "right",
    fontFamily: "font3",
    color: colors.darkbrown,
  },
});
