<template>
  <div>
    <router-view/>
  </div>

  <el-config-provider :locale="locale"></el-config-provider>
</template>

<style>
</style>

<script>

import {Location, Menu as IconMenu} from '@element-plus/icons'

export default {
  name:"Layout",

}
</script>