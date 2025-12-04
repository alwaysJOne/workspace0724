import React from 'react'
import { Container, Title } from '../components/styld/common'
import PostList from '../components/PostList'

const PostListPage = () => {
  return (
    <Container>
        <Title>게시글 목록</Title>
        <PostList />
    </Container>
  )
}

export default PostListPage