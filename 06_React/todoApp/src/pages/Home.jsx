import React from 'react'
import { AreaTitle, CategoryArea, CategoryCount, CategoryItem, CategoryList, CategoryName, HomeContainer, StateArea, StateCard, StateLabel, StateNumber, Title } from './Home.styled'
import { useTodos } from '../context/TodoContext'
import { CATEGORY_NAMES, CATEGORYS, ROUTES } from '../routes/routePaths';

const Home = () => {
  const { getState } = useTodos();
  const state = getState();

  const categories = [
    { name: CATEGORY_NAMES[CATEGORYS.WORK], value: CATEGORYS.WORK, count: state.byCategory.work },
    { name: CATEGORY_NAMES[CATEGORYS.STUDY], value: CATEGORYS.STUDY, count: state.byCategory.study },
    { name: CATEGORY_NAMES[CATEGORYS.HEALTH], value: CATEGORYS.HEALTH, count: state.byCategory.health },
  ]

  return (
    <HomeContainer>
      <Title>Dashboard</Title>

      <StateArea>
        <StateCard>
          <StateNumber>{state.total}</StateNumber>
          <StateLabel>전체 할일</StateLabel>
        </StateCard>
        <StateCard>
          <StateNumber>{state.pending}</StateNumber>
          <StateLabel>미완료</StateLabel>
        </StateCard>
        <StateCard>
          <StateNumber>{state.completed}</StateNumber>
          <StateLabel>완료</StateLabel>
        </StateCard>
      </StateArea>

      <CategoryArea>
        <AreaTitle>카테고리별 할일</AreaTitle>
        <CategoryList>
          { categories.map(category => (
            <CategoryItem key={categories.value} to={ROUTES.CATEGORY(category.value)}>
              <CategoryName>{category.name}</CategoryName>
              <CategoryCount>{category.count}</CategoryCount>
            </CategoryItem>
          ))}
        </CategoryList>
      </CategoryArea>
    </HomeContainer>
  )
}

export default Home