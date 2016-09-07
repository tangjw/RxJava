package com.tjw.rxjava;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/7.
 */
public class Repo {
	
	/**
	 * code : 0
	 * message : success
	 * entity : {"data":{"id":1,"name":"demo1","value":"this is demo1","links":{"rel":"self","href":"/v1/demo/1"}}}
	 */
	
	private int code;
	private String message;
	/**
	 * data : {"id":1,"name":"demo1","value":"this is demo1","links":{"rel":"self","href":"/v1/demo/1"}}
	 */
	
	private EntityBean entity;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public EntityBean getEntity() {
		return entity;
	}
	
	public void setEntity(EntityBean entity) {
		this.entity = entity;
	}
	
	public static class EntityBean {
		/**
		 * id : 1
		 * name : demo1
		 * value : this is demo1
		 * links : {"rel":"self","href":"/v1/demo/1"}
		 */
		
		private DataBean data;
		
		public DataBean getData() {
			return data;
		}
		
		public void setData(DataBean data) {
			this.data = data;
		}
		
		public static class DataBean {
			private int id;
			private String name;
			private String value;
			/**
			 * rel : self
			 * href : /v1/demo/1
			 */
			
			private LinksBean links;
			
			public int getId() {
				return id;
			}
			
			public void setId(int id) {
				this.id = id;
			}
			
			public String getName() {
				return name;
			}
			
			public void setName(String name) {
				this.name = name;
			}
			
			public String getValue() {
				return value;
			}
			
			public void setValue(String value) {
				this.value = value;
			}
			
			public LinksBean getLinks() {
				return links;
			}
			
			public void setLinks(LinksBean links) {
				this.links = links;
			}
			
			public static class LinksBean {
				private String rel;
				private String href;
				
				public String getRel() {
					return rel;
				}
				
				public void setRel(String rel) {
					this.rel = rel;
				}
				
				public String getHref() {
					return href;
				}
				
				public void setHref(String href) {
					this.href = href;
				}
			}
		}
	}
}
