<template>
  <div id="search">
    <section class="card txslist-container">
      <p class="title">Search Result</p>
      <p class="title-2">Transaction List</p>
      <el-table :data="transactionList" stripe style="width: 100%">
        <el-table-column
          prop="transactionid"
          label="ID"
          width="80"
          fixed
        ></el-table-column>
        <el-table-column
          prop="sum"
          label="金额"
          width="80"
          fixed
        ></el-table-column>
        <el-table-column prop="input" label="付款方"></el-table-column>
        <el-table-column prop="output" label="收款方"></el-table-column>
        <el-table-column fixed="right" label="操作" width="60">
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
      <p class="title-2">Blockchain List</p>
      <el-table :data="blockList" stripe style="width: 100%">
        <el-table-column
          prop="height"
          label="高度"
          width="80"
          fixed
        ></el-table-column>
        <el-table-column
          prop="nonce"
          label="Nonce"
          width="80"
          fixed
        ></el-table-column>
        <el-table-column prop="hash" label="Hash"></el-table-column>
        <el-table-column fixed="right" label="操作" width="60">
          <template slot-scope="scope">
            <el-button
              @click="get_block_detail(scope.row)"
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
  name: "search",
  data() {
    return {
      keyword: this.$route.query.q,
      transactionList: [],
      blockList: []
    };
  },
  created() {
    this.keyword = this.$route.query.q;

    this.$axios({
      method: "post",
      url: "/homePage/search",
      data: {
        keyword: this.keyword
      }
    })
      .then(response => {
        const data = response.data;
        if (data.status.Ack === "success") {
          this.transactionList = data.transactionList;
          this.blockList = data.blockList;
          this.$message({
            message: "搜索成功",
            type: "success"
          });
        } else {
          this.$message.error(data.status.ErrorMessage);
        }
      })
      .catch(() => {
        this.$message.error("搜索失败");
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
    },
    get_block_detail: function(row) {
      this.$router.push({
        path: "block",
        query: {
          q: row.blockid
        }
      });
    }
  }
};
</script>
