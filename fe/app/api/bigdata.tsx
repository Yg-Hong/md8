// 나중에 URL 확정되면 사용할 공통 주소
// import axios from "./instance";

// 임시로 사용할 axios
import axios from "axios";
const baseURL = "http://j10a208a.p.ssafy.io:8084/api";

// 전체 두드림길 조회
const getAllDudurims = async (lat, lon) => {
  try {
    const response = await axios({
      method: "get",
      url: `${baseURL}/bigdata/dudurim?lat=${lat}&lon=${lon}`,
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 상세 두드림길 조회
const getOneDudurim = async (trackingId) => {
  try {
    const response = await axios({
      method: "get",
      url: `${baseURL}/bigdata/dudurim/${trackingId}`,
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 시도 조회 API
const getDistrict = async (lat, lng) => {
  try {
    const response = await axios({
      method: "get",
      url: `${baseURL}/bigdata/weather/address?lat=${lat}&lon=${lng}`,
    });
    return response.data;
  } catch (error) {
    console.log("getDistrict API 에러:", error);
  }
};

export { getAllDudurims, getOneDudurim, getDistrict };
