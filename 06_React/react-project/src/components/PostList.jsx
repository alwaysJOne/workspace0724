import React, { useEffect, useState } from 'react'
import usePostStore from '../store/postStore'
import { Content, Loading, LoadingOverlay, PostCard, Title } from './Post.styled';
import { Button, Container } from './styld/common';

const PostList = () => {
    const [deletePostId, setDeletePostId] = useState(null);
    const {deleteLoading, posts, getPosts, loading, error, deletePost} = usePostStore();

    useEffect(() => {
        getPosts();
    },[])

    const habdleDlelete = async (id) => {
        setDeletePostId(id);
        await deletePost(id);
        setDeletePostId(null);
    }


    if (loading) return <Loading>로딩중...</Loading>
    if (error) return <Error>에러발생 : {error}</Error>
    
    return (
        <Container>
            {posts.map(post => (
                <PostCard key={post.id}>
                    <Title>{post.title}</Title>
                    <Content>{post.body}</Content>
                    <Button>수정</Button>
                    <Button
                        // disabled={deleteLoading}
                        onClick={() => habdleDlelete(post.id)}
                    >
                        {deletePostId === post.id ? "삭제중..." : "삭제"}
                    </Button>
                    {deletePostId === post.id && (
                        <LoadingOverlay>
                            <div>삭제중...</div>
                        </LoadingOverlay>
                    )}
                </PostCard>
            ))}
        </Container>
    )
}

export default PostList