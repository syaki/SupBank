<template>
  <div class="search">
    <AppHeader/>
    <div class="main">
      <main role="main">
        <div class="main-inner">
          <div class="transactionListCard card">
            <table class="listTable">
              <caption>Search result:</caption>
              <tr>
                <th>ID</th>
                <th>Input</th>
                <th>Output</th>
                <th>Sum</th>
              </tr>
              <tr
                v-for="t in searchResultList"
                :key="t.id"
                v-on:click="getTransactionDetail(t.transactionid)"
              >
                <td>{{t.transactionid}}</td>
                <td>{{t.input}}</td>
                <td>{{t.output}}</td>
                <td>{{t.sum}}</td>
              </tr>
            </table>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "search",
  components: {
    AppHeader
  },
  data() {
    return {
      searchText: this.$route.query.q,
      searchResultList: [],
      pages: 1,
      page: 1
    };
  },
  created() {
    const searchText = this.$route.query.q;

    this.$axios({
      method: "post",
      url: "http://192.168.1.103:8990/homePage/search",
      data: {
        keyword: searchText
      }
    })
      .then(response => {
        const data = response.data;
        if (data.status.Ack === "success") {
          this.searchResultList = data.transactionList;
        } else {
          alert(data.status.ErrorMessage);
        }
      })
      .catch(error => {
        alert(error);
      });
  },
  methods: {
    getTransactionDetail: function(id) {
      this.$router.push({
        path: "transaction",
        query: {
          q: id
        }
      });
    }
  }
};
</script>

<style scoped>
.main-inner {
  position: relative;
  width: 1000px;
  padding: 0 16px;
  margin: 14px auto;
}

.searchCard .desc {
  margin-top: 18px;
  margin-bottom: 30px;
  font-size: 18px;
  color: #8590a6;
}

.listTable {
  display: flex;
  display: -webkit-box;
  display: -ms-flexbox;
  align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  justify-content: center;
  width: 100%;
}

.listTable caption {
  font-size: 24px;
  margin-bottom: 16px;
  color: #0084ff;
  text-align: left;
  width: 920px;
}

.listTable td {
  padding: 4px 8px;
}
</style>
