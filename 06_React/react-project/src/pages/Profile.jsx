import React from 'react'
import { useLocation, useParams } from 'react-router-dom'

const Profile = () => {
    const {username} = useParams();

    const {search} = useLocation(); //모든 쿼리스트림
    console.log(search);
    const query = new URLSearchParams(search);

    const sort = query.get('sort');

    return (
    <>
        {sort}
        <div>프로필페이지 입니다.</div>
        <p>{username}의 프로필</p>
    </>
  )
}

export default Profile