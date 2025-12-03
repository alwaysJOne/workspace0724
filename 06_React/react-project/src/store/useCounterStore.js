import { create } from "zustand";


const useCounterStore = create((set, get) => ({
    count: 0,
    //set의 콜백으로 상태를 바로 업데이트 할 수 있음(불변성 보장)
    //업데이트 순간의 최신 state를 함수로 전달받음
    increase : () => set((state) => ({count: state.count + 1})), 
    //store내부에서 현재상태 전체를 조회
    decrease : () => set({count : get().count - 1 }),
    reset : () => set({count : 0}),
}))

export default useCounterStore;

// create(() => ({초기값}))