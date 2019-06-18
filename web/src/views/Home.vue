<template>
  <div id="home">
    <section class="card search-container">
      <p class="title">Search Blockchain</p>
      <el-form :inline="true" :model="searchForm">
        <el-form-item
          class="search-input-container"
          prop="keyword"
          :rules="[{ required: true, message: '搜索框不能为空' }]"
        >
          <el-input
            class="search-input"
            v-model="searchForm.keyword"
            placeholder="You can lookup address, transactions, blocks or hash..."
          ></el-input>
        </el-form-item>
        <el-form-item class="search-btn-container">
          <el-button type="primary" icon="el-icon-search" @click="search"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
    </section>
    <section class="card">
      <p class="title">Transactions List</p>
      <el-table :data="transactionList" stripe style="width: 100%">
        <el-table-column
          prop="timestamp"
          label="日期"
          width="140"
          fixed
        ></el-table-column>
        <el-table-column
          prop="sum"
          label="金额"
          width="60"
          fixed
        ></el-table-column>
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
    </section>
    <section class="card">
      <p class="title">Blockchain List</p>
      <el-table :data="blockList" stripe style="width: 100%">
        <el-table-column
          prop="height"
          label="高度"
          width="80"
          fixed
        ></el-table-column>
        <el-table-column prop="hash" label="Hash"></el-table-column>
        <el-table-column prop="prehash" label="Prev Hash"></el-table-column>
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
  name: "home",
  data() {
    return {
      transactionList: [],
      blockList: [],
      searchForm: {
        keyword: ""
      }
    };
  },
  created() {
    this.$axios({
      method: "post",
      url: "/homePage/getLastTransaction",
      data: {}
    })
      .then(response => {
        const data = response.data;

        if (data.status.Ack === "success") {
          for (let i = 0; i < data.transactionList.length; i++) {
            let t = data.transactionList[i];
            t.timestamp = `${new Date(t.timestamp).getFullYear()}/${new Date(
              t.timestamp
            ).getMonth()}/${new Date(t.timestamp).getDate()} ${new Date(
              t.timestamp
            ).getHours()}:${new Date(t.timestamp).getMinutes()}`;
          }
          this.transactionList = data.transactionList;
        } else {
          this.$message.error("请先登录");
          sessionStorage.removeItem("username");
        }
      })
      .catch(() => {
        this.$message.error("获取数据失败");
      });

    this.$axios({
      method: "post",
      url: "/block/longestLegalChain",
      data: {
        pageNumber: "1"
      }
    })
      .then(response => {
        const data = response.data;

        if (data.status.Ack === "success") {
          this.blockList = data.longestLegalChain;
        } else {
          this.$message.error("请先登录");
          sessionStorage.removeItem("username");
        }
      })
      .catch(() => {
        this.$message.error("获取数据失败");
      });
  },
  methods: {
    search: function() {
      if (!this.searchForm.keyword || this.searchForm.keyword.length <= 0) {
        this.$message({
          message: "请输入搜索关键字",
          type: "warning"
        });
        return;
      }

      this.$router.push({
        path: "search",
        query: {
          q: this.searchForm.keyword
        }
      });
    },
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

<style scoped>
.search-input-container {
  margin-top: 50px;
  margin-left: 90px;
}

.search-input {
  width: 640px;
}

.search-btn-container {
  margin-top: 50px;
}
</style>
