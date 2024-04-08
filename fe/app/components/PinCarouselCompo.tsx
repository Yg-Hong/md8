import Pin from "@/atoms/Pin";
import React, { useEffect, useState } from "react";
import { Text, View, Dimensions } from "react-native";
import Carousel from "react-native-snap-carousel";

const { width: screenWidth } = Dimensions.get("window");

interface Pin {
  imageId: number;
  authorId: number;
  uri: string;
}

interface PinCarouselCompoProps {
  pins: Array<Pin>;
  index: number;
  setCarouselIndex: (value: number) => void;
}

const PinCarouselCompo = (props: PinCarouselCompoProps) => {
  const _renderItem = ({ item }: { item: Pin }) => {
    return <Pin {...item} />;
  };

  const carouselRef = React.useRef<Carousel<Pin> | null>(null);

  // index가 변경될 때마다 carouselRef.current를 이용하여 해당 index로 이동
  useEffect(() => {
    if (carouselRef.current) {
      carouselRef.current.snapToItem(props.index);
    }
  }, [props.index]);

  return (
    <Carousel
      ref={carouselRef}
      data={props.pins}
      renderItem={_renderItem}
      layout="stack"
      sliderWidth={screenWidth}
      itemWidth={screenWidth * 0.8}
      onSnapToItem={(index) => props.setCarouselIndex(index)}
    />
  );
};

export default PinCarouselCompo;
