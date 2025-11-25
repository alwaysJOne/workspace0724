import React, { Component } from 'react'
import Comment from './Comment';

const serverCommentData = [
    {
        id: 1,
        message: "안녕하세요. 최지원입니다.",
    },{
        id: 2,
        message: "이제 날씨가 조금 춥습니다.",
    },{
        id: 3,
        message: "집갈때 붕어빵 사가야지.",
    }
]

export default class CommentBox extends Component {
    constructor(props){
        super(props)

        this.state = {
            commentList: [],
        }
    }

    //CommentBox화면에 렌더링되는 순간
    componentDidMount(){
        setInterval(() => {
            const {commentList} = this.state;

            if(commentList.length < serverCommentData.length){
                const nextComment = serverCommentData[commentList.length];
                this.setState({
                    commentList: [...commentList, nextComment],
                })
            } else {
                this.setState({
                    commentList: [],
                })
            }
        }, 3000);
    }

    render() {
        const {commentList} = this.state;

        return (
            <>
                {commentList.map(c => 
                        <Comment
                            key={c.id}
                            id={c.id}
                            message={c.message}
                        />
                    )
                }
            </>
        )
    }
}
