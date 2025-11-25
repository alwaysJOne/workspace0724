import React from 'react'
import { 
    Container,
    Thumbnail,
    ChannelInfo,
    ChannelLogo,
    ChannelDetails,
    Title,
    ChannelName,
    ViewInfo
 } from './VideoCard.styled'

const VideoCard = ({video}) => {
  return (
    <Container>
        <Thumbnail
            src={video.sumbnail}
            alt={video.title}
        />
        <ChannelInfo>
            <ChannelLogo 
                src={video.logo} 
                alt={video.channelName} 
            />
            <ChannelDetails>
                <Title>{video.title}</Title>
                <ChannelName>{video.channelName}</ChannelName>
                <ViewInfo>{video.views} 조회수 | {video.date}</ViewInfo>
            </ChannelDetails>
        </ChannelInfo>
    </Container>
  )
}

export default VideoCard