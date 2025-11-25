import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Hello from './components/Hello'
import Head from './components/Head'
import Videos from './components/Videos'

const videoList = [{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
},{
  title: "[추석특집] 빵빵이의 일상!",
  channelName: "빵빵이의 일상",
  sumbnail: "https://i.ytimg.com/an_webp/fyHwfLxBugQ/mqdefault_6s.webp?du=3000&sqp=CLDfk8kG&rs=AOn4CLCJUvk5PhaadzjEFqbHF9Xcm_No4A",
  logo: "https://yt3.googleusercontent.com/wYRkjS6E0mMZ-np2jNwjVaCNzQMpxs1VkdQ_p25oe0aaSj0awd7f9xRUcrwI6rVOQE7kjZQ6l4A=s160-c-k-c0x00ffffff-no-rj",
  views: '8.3만',
  date: "1개월 전",
}]

function App() {

  return (
    <>
      {/* <Hello />
      <Hello />
      <Hello />
      <Hello /> */}
      {/* <Head type="h2" /> */}
      {/* <Head>
        <h3>무엇을 도와드릴까요?</h3>
      </Head> */}
      <Videos videoList={videoList} />
    </>
  )
}

export default App
