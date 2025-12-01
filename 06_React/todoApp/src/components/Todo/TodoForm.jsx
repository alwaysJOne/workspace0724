import React, { useState } from 'react'
import { AddButton, FormContainer, Input, Select } from './TodoForm.styled'

const TodoForm = ({ onAdd }) => {
    const [text, setText] = useState('');
    const [category, setCategory] = useState('work');

    const handleSubmit = () => {
        if(text.trim() === '') return;

        onAdd(text, category);
        setText('');
    }

    const handleKeyDown = (e) => {
        if(e.key === 'Enter'){
            handleSubmit();
        }
    }
  return (
    <FormContainer>
        <Select value={category} onChange={ ev => setCategory(ev.target.value)}>
            <option value="study">학습</option>
            <option value="work">업무</option>
            <option value="health">건강</option>
        </Select>
        <Input
            type='text'
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder='할일을 입력...'
            onKeyDown={handleKeyDown}
        />
        <AddButton onClick={handleSubmit}>
            추가
        </AddButton>
    </FormContainer>
  )
}

export default TodoForm