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
                <th>Height</th>
                <th>Hash</th>
                <th>Nonce</th>
                <th>Time</th>
              </tr>
              <tr v-for="t in searchResultList" :key="t.height">
                <td>{{t.height}}</td>
                <td>{{t.hash}}</td>
                <td>{{t.nonce}}</td>
                <td>{{`${new Date(t.timestamp).getFullYear()}/${new Date(t.timestamp).getMonth()}/${new Date(t.timestamp).getDate()} ${new Date(t.timestamp).getHours()}:${new Date(t.timestamp).getMinutes()}`}}</td>
              </tr>
            </table>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';

export default {
  name: 'search',
  components: {
    AppHeader,
  },
  data() {
    return {
      searchText: this.$route.query.q,
      searchResultList: [],
      pages: 1,
      page: 1,
    };
  },
  created() {
    const searchText = this.$route.query.q;
    const token = localStorage.getItem('token');

    this.$axios({
      method: 'post',
      url: '/api/s',
      data: {
        kw: searchText,
        token: token,
      },
    })
      .then(response => {
        const data = response.data;
        if (data.ack === 'success') {
          this.searchResultList = data.data.searchlist;
        } else {
          alert(data.data.msg);
        }
      })
      .catch(error => {
        alert(error);
      });
  },
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
