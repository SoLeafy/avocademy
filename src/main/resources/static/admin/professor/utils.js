async function getData(url, cno, headers = {}) {
  const options = {
    method: "POST",
	  body: JSON.stringify({
		cns_no: cno,
	}),
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
  };

  const res = await fetch(url, options);
  const data = await res.json();
  if(res.ok) {
    return data;
  } else {
    throw Error(data);
  }
}

async function postData(url, params, headers = {}) {
  const options = {
    method: "POST",
    body: JSON.stringify(params),
    headers: {
      "Content-Type": "application/json",
      ...headers,
    }
  }

  const response = await fetch(url, options);
  const data = await response.json();
  if(response.ok) {
    return data;
  } else {
    throw Error(data);
  }
}

async function updateData(url, params, headers = {}) {
  const options = {
    method: "POST",
    body: JSON.stringify(params),
    headers: {
      "Content-Type": "application/json",
      ...headers,
    }
  }

  const response = await fetch(url, options);
  //const data = await response.json();
  if(response.ok) {
    return response;
  } else {
    throw Error(response);
  }
}


function makeUserNoObj(uno) {
	return {user_no: uno};
}

function getNavbarRange(tzStart, tzEnd, viewType) {
  var start = tzStart.toDate();
  var end = tzEnd.toDate();
  var middle;
  if (viewType === 'month') {
    middle = new Date(start.getTime() + (end.getTime() - start.getTime()) / 2);

    return moment(middle).format('YYYY-MM');
  }
  if (viewType === 'day') {
    return moment(start).format('YYYY-MM-DD');
  }
  if (viewType === 'week') {
    return moment(start).format('YYYY-MM-DD') + ' ~ ' + moment(end).format('YYYY-MM-DD');
  }
  throw new Error('no view type');
}
