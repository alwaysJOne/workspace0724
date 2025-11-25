import styled from "styled-components";

export const Container = styled.div`
    width: 320px;
    background: #fff;
`

export const Thumbnail = styled.img`
    width: 100%;
    height: 180px;
`

export const ChannelInfo = styled.div`
    display: flex;
    align-items: flex-start;
`

export const ChannelLogo = styled.img`
    width: 36px;
    height: 36px;
    border-radius: 50pc;
    margin-right: 12px;
`

export const ChannelDetails = styled.div`
    flex-grow: 1;
    text-align: left;
`

export const Title = styled.h3`
    font-size: 16px;
    font-weight: 500;
    margin: 0 0 4px 0;
`

export const ChannelName = styled.p`
    font-size: 14px;
    color: gray;
    margin: 0 0 4px 0;
`

export const ViewInfo = styled.p`
    font-size: 14px;
    color: gray;
    margin: 4px 0 0 0;
    font-weight: 400;
`