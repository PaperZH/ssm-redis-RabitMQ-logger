<template>
  <div class="wrapper">
    <fj-Header></fj-Header>
    <fj-Sidebar></fj-Sidebar>
    <div class="content">
      <transition name="move" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
    import fjHeader from './common/Header.vue'
    import fjSidebar from './common/Sidebar.vue'
    import {uploadFile} from '../../service/UserService.js'

    export default {
      components: {
        fjHeader, fjSidebar
      },
      mounted() {
        this.load();
      },
      methods:{
        load() {
           
          //  loadData().then(function(response){
          //    console.log(response);
          //  });
        }
      }
    }
</script>