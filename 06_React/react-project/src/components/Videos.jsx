import React from 'react'
import styled from 'styled-components';
import VideoCard from './VideoCard';

const Container = styled.div`
    display: flex;
    justify-content: flex-start;
    gap: 16px;
    flex-wrap: wrap;
`

const Videos = ({videoList}) => {
  return <Container>
    {videoList.map((v, index) => <VideoCard key={index} video={v}/>)}
  </Container>
}

export default Videos