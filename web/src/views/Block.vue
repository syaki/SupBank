<template>
  <div id="block">
    <section class="card">
      <p class="title">Block Detail</p>
      <el-table :data="blockList" stripe style="width: 100%">
        <el-table-column
          prop="blockid"
          label="ID"
          width="100"
          fixed
        ></el-table-column>
        <el-table-column
          prop="height"
          label="高度"
          width="80"
        ></el-table-column>
        <el-table-column prop="hash" label="Hash"></el-table-column>
        <el-table-column prop="prehash" label="Prev Hash"></el-table-column>
      </el-table>
      <p class="title-2">Transactions List:</p>
      <el-table :data="transactionList" stripe style="width: 100%">
        <el-table-column prop="transactionid" label="ID"></el-table-column>
        <el-table-column label="操作" width="80">
          <template slot-scope="scope">
            <el-button
              @click="get_txs_detail(scope.row)"
              type="text"
              size="small"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </section>
  </div>
</template>

<script>
export default {
  name: "block",
  data() {
    return {
      id: this.$route.query.q,
      blockList: [],
      transactionList: []
    };
  },
  created() {
    this.id = this.$route.query.q;

    this.$axios({
      method: "post",
      url: "/block/getBlockById",
      data: {
        id: this.id
      }
    })
      .then(response => {
        const data = response.data;
        if (data.status.Ack === "success") {
          if (data.transactionList.length > 0) {
            // for (let i = 0; i < data.transactionList.length; i++) {
            //   let t = data.transactionList[i];
            //   let time = new Date(t.timestamp);
            //   t.timestamp =
            //     time.getFullYear() +
            //     "/" +
            //     (time.getMonth() + 1) +
            //     "/" +
            //     time.getDate() +
            //     "  " +
            //     time.getHours() +
            //     ":" +
            //     time.getMinutes() +
            //     ":" +
            //     time.getSeconds();
            // }
          }
          this.transactionList = data.transactionList;
          this.blockList = data.blockList;
        } else {
          this.$message.error(data.status.ErrorMessage);
        }
      })
      .catch(() => {
        this.$message.error("获取数据失败");
      });
  },
  methods: {
    get_txs_detail: function(row) {
      this.$router.push({
        path: "transaction",
        query: {
          q: row.transactionid
        }
      });
    }
  }
};
</script>

<style scoped></style>
