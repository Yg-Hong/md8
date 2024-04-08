import axios from "axios";
import { TokenAtom } from "@/recoil/UserAtom";
import { useRecoilValue } from "recoil";

// userToken 받아오기
const token = useRecoilValue(TokenAtom);

// 추가적으로 params, headers를 지정할 수 있음.
// axios 인스턴스 생성
// baseURL은 서버의 기본 URL
const instance = axios.create({
  baseURL: "http://j10a208a.p.ssafy.io:8082/api",
  withCredentials: true,
});

export default instance;
