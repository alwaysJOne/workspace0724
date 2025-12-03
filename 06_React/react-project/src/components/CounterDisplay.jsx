import React from 'react'
import styled from 'styled-components'
import useCounterStore from '../store/useCounterStore'

const DisplayContainer = styled.div`
    font-size: 32px;
    margin: 16px;
    padding: 20px;
    border-radius: 8px;
`

const CounterDisplay = () => {
    // const count = useCounterStore(state => state.count);
    const { count } = useCounterStore();
    
    return (
        <DisplayContainer>
            현재 카운트 : <strong>{count}</strong>
        </DisplayContainer>
    )
}

export default CounterDisplay