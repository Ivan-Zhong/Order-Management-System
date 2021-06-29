import { createStore } from 'vuex'
import axios from "axios";

export default createStore({
  state: {
    user:{
      id:"",
      name:"",
      tel:"",
      email:"",
      password:"",
      identity:""  
    },
  },
  mutations: {
    get_useinfo1(state,data){
      state.user.tel = data.data.tel;
      state.user.email = data.data.email;
      state.user.identity = data.data.identity;//获得本人登陆的信息
}
  },
  actions: {
    get_useinfo({commit,state}){
      axios.get("/api/person/read/own")
        .then((response) => {
            if(response.data.message == "success")
            {
              commit("get_useinfo1",response.data)
              callback()
            }
        }).catch((error)=>{
          state.user.name = 'zyf';
          state.user.tel = '14000000000';
          state.user.email = "1234567890@qq.com";
          state.user.identity = "root";
          })
      },  
  },
  modules: {
    
  }
})
