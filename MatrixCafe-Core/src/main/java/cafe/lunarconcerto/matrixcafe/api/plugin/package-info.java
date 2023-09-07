/**
 * <h1>插件系统</h1>
 * <p>
 *     已实现:
 *     <ul>
 *         <li>插件定义</li>
 *         <li>插件加载</li>
 *         <li>插件间依赖</li>
 *         <li>插件库依赖解析</li>
 *     </ul>
 * </p>
 * 插件可执行的操作行为较多, 由于存在一些不可逆的修改, 所以无法实现对插件的热更新和实时卸载, 但是热加载是可行的.
 * @author lunarconcerto
 * @version 1.0
 *
 * @see cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation @PluginInformation
 * @see cafe.lunarconcerto.matrixcafe.api.plugin.Plugin 插件行为类
 */
package cafe.lunarconcerto.matrixcafe.api.plugin;