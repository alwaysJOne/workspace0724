import axios from "axios";
import { create } from "zustand";

const usePostStore = create((set, get) => ({
    posts: [],
    loading: false,
    deleteLoading : false,
    error: null,
    getPosts: async () => {
        //처음 요청을 보낼 때 응답이 올때까지 로딩을 보여주고 에러는 비워줌.
        set({loading: true, error: null});

        try{
            const response = await axios.get('https://jsonplaceholder.typicode.com/posts');
            set({posts: response.data, loading: false});
        }catch(error){
            set({loading: false, error: error.message});
        }
    },
    deletePost: async (id) => {
        set({deleteLoading: true, error: null});

        try{
            await axios.delete(`https://jsonplaceholder.typicode.com/posts/${id}`);
            set(state => ({
                posts: state.posts.filter(post => post.id !== id),
                deleteLoading: false,
            }));
        }catch(error){
            set({deleteLoading: false, error: error.message});
        }
    }
}));

export default usePostStore;