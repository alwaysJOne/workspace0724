import React, { Component } from 'react'
import styled from 'styled-components'


const Container = styled.div`
    border: 1px solid gray;
    display: flex;
`

export default class Comment extends Component {
  constructor(props){
    super(props)

    this.state = {}
  }

//    componentDidMount(){
//         console.log(`componentDidMount : ${this.props.id}`);
//     }

//     //컴포넌트가 업데이트 된 후 호출
//     componentDidUpdate(prevProps, preveState){
//         console.log(`componentDidUpdate : ${this.props.id}`);
//     }

//     //컴포넌트가 언마운트 될 때 호출
//     componentWillUnmount(){
//       console.log(`componentWillUnmount : ${this.props.id}`);
//     }

        //업데이트 바로 전에 호출
        shouldComponentUpdate(nextProps, nextState){
            console.log(`shouldComponentUpdate : ${this.props.id}`);

            return false;
        }

  render() {
    return (
      <Container>
        <span>{this.props.message}</span>
      </Container>
    )
  }
}
