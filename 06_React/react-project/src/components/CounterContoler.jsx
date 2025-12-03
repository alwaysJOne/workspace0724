import React from 'react'
import styled from 'styled-components'
import useCounterStore from '../store/useCounterStore';

const Button = styled.button`
    padding: 8px 16px;
    border: none;
    background: #5656f1;
    border-radius: 4px;
    color: white;
    cursor: pointer;
    margin-right: 12px;

    &:hover{
        scale: 0.98;
    }

    &:last-child{
        margin-right: 0px;
    }
`

const CounterContoler = () => {
    const { increase, decrease, reset } = useCounterStore();
  return (
    <div>
        <Button onClick={increase}> + 1 </Button>
        <Button onClick={decrease}> - 1 </Button>
        <Button onClick={reset}> 초기화 </Button>
    </div>
  )
}

export default CounterContoler