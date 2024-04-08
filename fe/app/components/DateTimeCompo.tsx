import colors from "@/assets/color.js";
import { StyleSheet, Text, View } from "react-native";

// createdAt 형식 : "2024-03-14 17:26:59.789"
export default function DateTimeCompo({ createdAt }) {
  // string을 date 타입으로 변환
  const createdDate = new Date(createdAt);

  // 작성일 년/월/일 구하기
  const year = createdDate.getFullYear();
  const month = createdDate.getMonth() + 1;
  const date = createdDate.getDate();

  // 로컬 날짜/시간 구하기
  const now = new Date();

  // 시간 차이 구하기
  const timeGap = (now.getTime() - createdDate.getTime()) / (60 * 60 * 1000);

  return (
    // 당일이면 몇 시간 전인지 출력하고 아니면 날짜를 출력
    <View style={styles.container}>
      {createdDate.getTime() === now.getTime() ? (
        <Text style={styles.text}>{Math.floor(timeGap)}시간 전</Text>
      ) : (
        <Text style={styles.text}>
          {year}.{month}.{date}
        </Text>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    justifyContent: "center",
  },
  text: {
    textAlign: "center",
    color: colors.darkbrown,
    fontFamily: "font3",
  },
});
