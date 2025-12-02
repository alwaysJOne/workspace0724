import { Link } from "react-router-dom";
import styled from "styled-components";

export const HomeContainer = styled.div`
    max-width: 800px;
    margin: 0 auto;
`

export const Title = styled.h1`
    font-size: 42px;
    font-weight: bold;
    margin-bottom: 24px;
    color: #333;
`

export const StateArea = styled.div`
    display: flex;
    gap: 20px;
    margin-bottom: 40px;
    flex-wrap: wrap;
`

export const StateCard = styled.div`
    background: #ffffff;
    flex: 1;
    min-width: 200px;
    padding: 24px;
    border-radius: 12px;
`

export const StateNumber = styled.div`
    font-size: 36px;
    font-weight: bold;
    color: #5833ffff;
    margin-bottom: 8px;
`

export const StateLabel = styled.div`
    font-size: 14px;
    color: #666;
`

export const CategoryArea = styled.div`
    background: #fff;
    padding: 24px;
    border-radius: 12px;
    margin-bottom: 24px;
`

export const AreaTitle = styled.h2`
    font-size: 24px;
    margin-bottom: 16px;
    color: #333;
`

export const CategoryList = styled.div`
    display: flex;
    flex-direction: column;
    gap: 12px;
`

export const CategoryItem = styled(Link)`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 16px;
    background: #f3effdff;
    border-radius: 8px;
    text-decoration: none;
    color: #333;
`
export const CategoryName = styled.span`
    font-size: 16px;
    font-weight: 500;
`

export const CategoryCount = styled.span`
    background: #5833ffff;
    color: white;
    padding: 4px 12px;
    border-radius: 12px;
    font-size: 14px;
`
