import colors from "@/assets/color.js";
import React, { useCallback, useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  Button,
  SafeAreaView,
  Pressable,
  Image,
  Alert,
  TextInput,
} from "react-native";

import MenuBarCompo from "@/components/MenuBarCompo";
import SearchBoxCompo from "@/components/SearchBoxCompo";
import { ScrollView, FlatList } from "react-native-gesture-handler";
import { TouchableOpacity } from "@gorhom/bottom-sheet";
import IndexingCompo from "@/components/IndexingCompo";
import Roundplus from "@/assets/icons/Roundplus";
import Camera from "@/assets/icons/Camera";
import HashtagClose from "@/assets/icons/HashtagClose";
import InputBoxCompo from "@/components/InputBoxCompo";
import TrackCompo from "@/components/TrackCompo";
import Title from "@/atoms/Title";
import ButtonCompo from "@/components/ButtonCompo";
import * as ImagePicker from "expo-image-picker";
import ImageClose from "@icons/imageClose";
import { useNavigation } from "@react-navigation/native";
import { useRoute } from "@react-navigation/native";
import { constSelector } from "recoil";
import { createFeed } from "@/api/feed";

export default function FeedCreate() {
  const route = useRoute();
  // 선택한 산책 기록 객체
  const selectedTrack = route.params?.selectedTrack || {};

  const navigation = useNavigation();
  const [content, setContent] = useState("");
  const [hashTags, setHashTags] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);

  const gotoTrackingList = () => {
    navigation.navigate("SelectTracking");
  };

  const handleHashTag = (tag) => {
    if (tag && typeof tag === "string") {
      const inputValue = tag.trim();
      if (inputValue !== "") {
        const tags = inputValue.split(" ");
        const filteredTags = tags.filter((tag) => tag !== "");
        const newTags = [...hashTags, ...filteredTags];
        setHashTags(newTags.slice(0, 10));
      }
    }
  };
  const deleteHashtag = (index) => {
    const newHashTags = [...hashTags];
    newHashTags.splice(index, 1); // 해당 인덱스의 해시태그 삭제
    setHashTags(newHashTags); // 업데이트된 해시태그 배열 설정
  };

  // 이미지 선택 핸들러
  const handleImageSelection = async () => {
    const permissionResult =
      await ImagePicker.requestMediaLibraryPermissionsAsync();
    if (permissionResult.granted === false) {
      Alert.alert("카메라 롤에 접근 권한이 필요합니다!");
      return;
    }

    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.All,
      // allowsEditing: true,
      aspect: [4, 3],
      quality: 1,
      allowsMultipleSelection: true,
    });
    console.log("이;거어어어어", result);
    if (!result.canceled) {
      const newImages = [
        ...selectedImages,
        ...result.assets.map((asset) => asset.uri),
      ];
      setSelectedImages(newImages.slice(0, 10)); // 최대 10개까지만 저장
    }
  };

  // 이미지 삭제 핸들러
  const handleImageDelete = (index) => {
    const newImages = [...selectedImages];
    newImages.splice(index, 1); // 선택된 이미지 배열에서 해당 인덱스의 이미지 삭제
    setSelectedImages(newImages); // 업데이트된 이미지 배열 설정
  };

  //피드 생성 api 요청
  const onCreateFeed = () => {
    //userId 임의로 9990로 설정. 이후 recoil에서 들고 오기
    createFeed(
      9990,
      selectedTrack.trackingId,
      content,
      hashTags,
      selectedImages,
    )
      .then((response) => {
        console.log("FeedCreate.tsx에서 api 요청 성공", response);
        // navigation.navigate("") User 내 피드 목록으로 이동하기
      })
      .catch((error) => {
        console.log("FeedCreate.tsx에서 피드 생성 중 오류 발생:", error);
        // Alert.alert("피드 생성 중 오류가 발생했습니다.");
      });
  };

  // =======================================
  // console.log("나와줘", selectedTrack.trackingId);
  // console.log(selectedImages);
  // InputBoxCompo에서 입력된 값을 콘솔에 출력
  // console.log("입력된 내용:", content);
  // 해시태그 입력값이 들어있는 hashTags 배열의 각 요소 값을 콘솔에 출력
  console.log("해시태그들:", hashTags);
  return (
    <ScrollView style={styles.container}>
      <MenuBarCompo title="피드 만들기" />
      <View style={{ paddingHorizontal: 22, rowGap: 23 }}>
        {Object.keys(selectedTrack).length !== 0 ? (
          <TrackCompo result={selectedTrack} onPress={gotoTrackingList} />
        ) : (
          <View style={styles.selectContainer}>
            <Text style={styles.textStyle}>산책 기록을 선택해주세요</Text>
            <TouchableOpacity onPress={gotoTrackingList}>
              <Roundplus />
            </TouchableOpacity>
          </View>
        )}

        {/* //사진 업로드..? */}
        <TouchableOpacity onPress={handleImageSelection}>
          <View style={styles.cameraContainer}>
            <Camera />
          </View>
        </TouchableOpacity>
        <Text
          style={{
            marginTop: -15,
            color: colors.darkbrown,
            fontFamily: "font3",
          }}
        >
          이미지는 최대 10개까지 첨부할 수 있어요
        </Text>

        <View style={{ flexDirection: "row", flexWrap: "wrap" }}>
          {selectedImages.map((image, index) => (
            <View key={index} style={styles.selectedImage}>
              <Image source={{ uri: image }} style={styles.image} />
              <View style={{ position: "absolute", top: 0, right: 0 }}>
                <TouchableOpacity onPress={() => handleImageDelete(index)}>
                  <ImageClose width={12} />
                </TouchableOpacity>
              </View>
            </View>
          ))}
        </View>

        <InputBoxCompo
          mode="LARGE_INPUT"
          title="내용"
          placeholder="산책을 더 잘 기억하도록 느낌과 감정을 남겨보세요"
          content={content}
          setContent={setContent}
          // onSubmitEditing={}
        />

        {/* <InputBoxCompo
          mode="SMALL_INPUT"
          title="해시태그"
          placeholder="해시태그를 작성해 보세요"
          onSubmitEditing={handleHashTag}
        /> */}
        <View>
          <View style={styles.titleContainer}>
            <Title content={"해시태그"}></Title>
          </View>
          <TextInput
            style={styles.inputContainer}
            placeholder={"해시태그를 작성해 보세요"}
            // onChangeText={setContent}
            // value={content}
            onSubmitEditing={({ nativeEvent: { text } }) => handleHashTag(text)}
          />
        </View>

        <View style={styles.hashTagContainer}>
          {hashTags.map((tag, index) => (
            <View key={index} style={styles.oneHashTag}>
              <Text style={{ color: colors.darkbrown }}>#{tag}</Text>
              <View
                style={{
                  flexDirection: "column",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <TouchableOpacity onPress={() => deleteHashtag(index)}>
                  <HashtagClose />
                </TouchableOpacity>
              </View>
            </View>
          ))}
        </View>
        <ButtonCompo
          mode="ONE_BUTTON"
          textGreen={"피드 만들기"}
          onPressGreen={onCreateFeed}
        />
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  selectContainer: {
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
  textStyle: {
    color: colors.darkbrown,
    fontFamily: "font5",
  },
  cameraContainer: {
    width: "20%",
    aspectRatio: 1, // 이 부분을 추가하여 정사각형으로 만듭니다.
    paddingVertical: 9,
    paddingHorizontal: 7,
    alignItems: "center",
    justifyContent: "center",
    borderWidth: 1,
    borderColor: colors.beige,
    borderRadius: 8,
  },
  hashTagContainer: {
    flexDirection: "row",
    gap: 10,
    flexWrap: "wrap",
  },
  oneHashTag: {
    flexDirection: "row",
    justifyContent: "center",
    alignContent: "center",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: colors.beige,
    gap: 10,
    paddingHorizontal: 5,
    paddingVertical: 3,
  },
  selectedImage: {
    width: "20%",
    aspectRatio: 1,
    paddingVertical: 9,
    paddingHorizontal: 7,
    alignItems: "center",
    justifyContent: "center",
    borderRadius: 8,
    // backgroundColor: "teal",
  },
  image: {
    width: "100%",
    height: "100%",
  },
  deleteButton: {
    position: "absolute",
    top: 0,
  },
  titleContainer: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "flex-start",
    marginBottom: 3,
  },
  inputContainer: {
    height: 50,
    alignSelf: "stretch",
    alignItems: "flex-start",
    // backgroundColor: "orange",
    textAlignVertical: "top",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: colors.beige,
    paddingVertical: 15,
    paddingHorizontal: 5,
  },
});
